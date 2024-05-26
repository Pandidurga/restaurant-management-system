//loads the functions when page is refreshed
document.addEventListener('DOMContentLoaded', () => {
    loadMenuItems();
    loadTables();
    loadTablesDropdown();
    loadOrders();
});

//functions to load available menuItems
function loadMenuItems() {
    //url for fetching available menuItems
    fetch('http://localhost:8083/api/menu-items/get-available')
        .then(response => response.json())
        .then(data => {
            const menuItemsTable = document.getElementById('menu-items-table');
            const menuItemsBody = menuItemsTable.querySelector('tbody');
            menuItemsBody.innerHTML = '';
            //displaying the menuItems in tabular format
            data.forEach(item => {
                menuItemsBody.innerHTML += `
                    <tr>
                        <td>${item.menuItemId}</td>
                        <td>${item.itemName}</td>
                        <td>${item.unitPrice}</td>
                    </tr>
                `;
            });
        })
        //if any errors occur it will be displayed in cosole as error message
        .catch(error => console.error('Error fetching menu items:', error));
}

//function to load the vacant tables
function loadTables() {
    
    //url to fetch the vacant tables
    fetch('http://localhost:8083/api/restaurant-tables/get-vacant')
        .then(response => response.json())
        .then(data => {
            const tablesTable = document.getElementById('tables-table');
            const tablesBody = tablesTable.querySelector('tbody');
            tablesBody.innerHTML = '';

            //displays the tables in tabular format
            data.forEach(table => {
                tablesBody.innerHTML += `
                    <tr>
                        <td>${table.tableId}</td>
                        <td>${table.tableNumber}</td>
                        <td>${table.seats}</td>
                    </tr>
                `;
            });
        })
        //if any errors occur it will be displayed in cosole as error message
        .catch(error => console.error('Error fetching tables:', error));
}

//function to create new order 
function createOrder() {   
    const tableId = document.getElementById('table').value;
    //url to add the order to the backend and store it in db
    fetch('http://localhost:8083/api/orders/add?tableId=' + tableId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        //once order created successfully
        if (response.status === 201) {
            console.log("Order created successfully");
            alert('Order created successfully');
            loadOrders();//loads the orders
            loadTables();//refresh the table
            loadTablesDropdown();//refresh table dropdown
        } else {
            console.error("Failed to create order");
        }
    })
    .catch(error => console.error("Error creating order:", error));
}

//functions to load the tables dropdown in order create form
function loadTablesDropdown() {
    //url to fetch vacant tables from db
    fetch('http://localhost:8083/api/restaurant-tables/get-vacant')
        .then(response => response.json())
        .then(data => {
            const tableDropdown = document.getElementById('table');
            tableDropdown.innerHTML = '';
            //generates the dropdown table data
            data.forEach(table => {
                const option = document.createElement('option');
                option.value = table.tableId;
                option.textContent = `Table ${table.tableNumber} (${table.seats} seats)`;
                //append the child with existing dropdown options
                tableDropdown.appendChild(option);
            });
        })
        //if any error occur it will display in console as error message
        .catch(error => console.error('Error fetching tables:', error));
}

//function to cancel order when cancle button is clicked
function cancelOrder(orderId) {

    //url to set the order status to "CANCELLED"
    fetch(`http://localhost:8083/api/orders/cancel/${orderId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            console.log(`Order ${orderId} canceled successfully`);
            alert('Order canceled successfully');
            loadOrders();//refresh orders list
            loadTables();//refresh tables list
            loadTablesDropdown();//refresh tables dropdown menu
        } else {
            console.error(`Failed to cancel order ${orderId}`);
        }
    })
    .catch(error => console.error(`Error canceling order ${orderId}:`, error));
}

//To populate orders in table format
function loadOrders() {

    //url to fetch the list of orders
    fetch('http://localhost:8083/api/orders/get-all')
        .then(response => response.json())
        .then(data => {
            console.log('Fetched orders:', data);
            const ordersTable = document.getElementById('orders-table');
            const ordersBody = ordersTable.querySelector('tbody');

            //populates the Orders
            ordersBody.innerHTML = '';
            data.forEach(order => {
                const tableId = order.restaurantTable ? order.restaurantTable.tableId : 'N/A';
                ordersBody.innerHTML += `
                    <tr>
                        <td>${order.orderId}</td>
                        <td>${tableId}</td>
                        <td>${order.status}</td>
                        <td>${order.totalAmount}</td>
                        <td>${order.dateTime}</td>
                        <td>
                            

                            <button onclick="openAddItemModal(${order.orderId})">Add Items</button>
                            <button onclick="viewOrderItems(${order.orderId})">View Items</button>
                            <button onclick="generateBill(${order.orderId})">Generate Bill</button>
                            <button onclick="cancelOrder(${order.orderId})">Cancel</button>
                        </td>
                    </tr>
                    <tr id="order-items-${order.orderId}" style="display: none;">
                        <td colspan="5">
                            <div class="order-items-container"></div>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(error => console.error('Error fetching orders:', error));
}

//to view the orderredItems based on the order Id
function viewOrderItems(orderId) {
    const orderItemsRow = document.getElementById(`order-items-${orderId}`);
    const orderItemsContainer = orderItemsRow.querySelector('.order-items-container');
    
    if (orderItemsRow.style.display === 'none') {
        //url to fetch orderDetailas based on orderId
        fetch(`http://localhost:8083/api/order-details/get-by-order-id/${orderId}`)
            .then(response => response.json())
            .then(data => {
                const tableHtml = `
                    <table class="order-details-table">
                        <thead>
                            <tr>
                                <th>Order Detail ID</th>
                                <th>Item Name</th>
                                <th>Quantity</th>
                                <th>Unit Price</th>
                                <th>Total Cost</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${data.map(orderDetail => `
                                <tr>
                                    <td>${orderDetail.orderDetailId}</td>
                                    <td>${orderDetail.menuItem.itemName}</td>
                                    <td>${orderDetail.quantity}</td>
                                    <td>${orderDetail.unitPrice}</td>
                                    <td>${orderDetail.totalCost}</td>
                                    <td>
                                        <button onclick="editOrderItem(${orderId}, ${orderDetail.orderDetailId})">Edit</button>
                                        <button onclick="deleteOrderItem(${orderId}, ${orderDetail.orderDetailId})">Delete</button>
                                    </td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                `;
                orderItemsContainer.innerHTML = tableHtml;
                //to display the orderdetails in table's row
                orderItemsRow.style.display = 'table-row';
            })
            .catch(error => console.error('Error fetching order items:', error));
    } else {
        orderItemsRow.style.display = 'none';
    }
}

//function to open Add items form
function openAddItemModal(orderId) {
    document.getElementById('order-id').value = orderId;
    loadMenuItemsDropdown();
    document.getElementById('add-item-modal').style.display = 'block';
}

//function to close the Add items form
function closeAddItemModal() {
    document.getElementById('add-item-modal').style.display = 'none';
}

function loadMenuItemsDropdown() {
    fetch('http://localhost:8083/api/menu-items/get-available')
        .then(response => response.json())
        .then(data => {
            const menuItemSelect = document.getElementById('menu-item');
            menuItemSelect.innerHTML = '';
            data.forEach(item => {
                menuItemSelect.innerHTML += `<option value="${item.menuItemId}">${item.itemName}</option>`;
            });
        })
        .catch(error => console.error('Error fetching menu items:', error));
}

function addItemToOrder() {
    const orderId = document.getElementById('order-id').value;
    const menuItemId = document.getElementById('menu-item').value;
    const quantity = document.getElementById('quantity').value;

    const url = `http://localhost:8083/api/order-details/add?orderId=${orderId}&menuItemId=${menuItemId}&quantity=${quantity}`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            console.log('Item added successfully');
            closeAddItemModal();
            viewOrderItems(orderId);
            loadOrders();
        } else {
            console.error('Failed to add item');
        }
    })
    .catch(error => console.error('Error adding item:', error));
}

//function to open the orderItem edit form
function editOrderItem(orderId, orderDetailId) {
    fetch(`http://localhost:8083/api/order-details/get-by-id/${orderDetailId}`)
        .then(response => response.json())
        .then(data => {
            // Populate the menu items dropdown
            const menuItemSelect = document.getElementById('edit-menu-item');
            menuItemSelect.innerHTML = '';
            // Fetch available menu items and populate the dropdown
            fetch('http://localhost:8083/api/menu-items/get-available')
                .then(response => response.json())
                .then(menuItems => {
                    menuItems.forEach(item => {
                        const option = document.createElement('option');
                        option.value = item.menuItemId;
                        option.textContent = item.itemName;
                        menuItemSelect.appendChild(option);
                    });
                    // Set the selected menu item in the dropdown
                    menuItemSelect.value = data.menuItem.menuItemId;
                })
                .catch(error => console.error('Error fetching menu items:', error));

            // Set the quantity in the input field
            document.getElementById('edit-quantity').value = data.quantity;

            // Set the data attributes for order ID and order detail ID
            document.getElementById('edit-order-item-form').setAttribute('data-order-id', orderId);
            document.getElementById('edit-order-item-form').setAttribute('data-order-detail-id', orderDetailId);

            // Show the edit order item modal
            document.getElementById('edit-order-item-modal').style.display = 'block';
        })
        .catch(error => console.error('Error fetching order item:', error));
}


//function to update the orderitems
function updateOrderItem() {
    const orderId = document.getElementById('edit-order-item-form').getAttribute('data-order-id');
    const orderDetailId = document.getElementById('edit-order-item-form').getAttribute('data-order-detail-id');
    const menuItemId = document.getElementById('edit-menu-item').value;
    const quantity = document.getElementById('edit-quantity').value;

    const url = `http://localhost:8083/api/order-details/update/${orderDetailId}?orderId=${orderId}&menuItemId=${menuItemId}&quantity=${quantity}`;

    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            console.log('Order item updated successfully');
            viewOrderItems(orderId);
            loadOrders();
            document.getElementById('edit-order-item-modal').style.display = 'none';
        } else {
            console.error('Failed to update order item');
        }
    })
    .catch(error => console.error('Error updating order item:', error));
}

//to close the orderedItem edit form
function closeEditOrderItemModal() {
    document.getElementById('edit-order-item-modal').style.display = 'none';
}

//function to delete the orderItem
function deleteOrderItem(orderId, orderDetailId) {
    fetch(`http://localhost:8083/api/order-details/delete/${orderDetailId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            console.log(`Order item ${orderDetailId} deleted successfully`);
            alert('Order item deleted successfully');
            viewOrderItems(orderId);
            loadOrders();
        } else {
            console.error(`Failed to delete order item ${orderDetailId}`);
        }
    })
    .catch(error => console.error(`Error deleting order item ${orderDetailId}:`, error));
}


// Function to generate the bill
function generateBill(orderId) {
    // Fetch order details to include in the bill
    fetch(`http://localhost:8083/api/orders/get-by-id/${orderId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Order not found');
            }
            return response.json();
        })
        .then(order => {
            if (order.status === 'CANCELLED') {
                alert('Order is cancelled');
                throw new Error('Order is cancelled');
            }
            // Fetch order details to include in the bill
            return fetch(`http://localhost:8083/api/order-details/get-by-order-id/${orderId}`);
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Order details not found');
            }
            return response.json();
        })
        .then(orderDetails => {
            if (orderDetails.length === 0) {
                alert('There is no item to bill');
                throw new Error('Order has no items');
                
            }
            // Process order details and generate the bill HTML
            let billHtml = `
                <h2>Bill for Order ID: ${orderId}</h2>
                <h3>Table Number: ${orderDetails[0].order.restaurantTable.tableNumber}</h3>
                <p>Date-Time:${orderDetails[0].order.dateTime}<p>
                <h3>Order Details:</h3>
                <ul>
            `;
            // Add order details to the bill
            orderDetails.forEach(orderDetail => {
                billHtml += `
                    <li>${orderDetail.menuItem.itemName} - Quantity: ${orderDetail.quantity} - Unit Price: ${orderDetail.unitPrice} -totalCost:${orderDetail.totalCost}</li>
                `;
            });
            // Fetch the pre-calculated total amount from the order
            let totalAmount = orderDetails[0].order.totalAmount;  // Assuming the total amount is stored in the order object
            
            billHtml += `</ul><p>Total Amount: ${totalAmount}</p>`;
            // Display the bill in a modal or other UI element
            displayBillModal(billHtml);

            // Trigger the generate bill endpoint to update statuses
            return fetch(`http://localhost:8083/api/orders/generate-bill/${orderId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update order and table status');
            }
            loadTables();
            loadTablesDropdown();
            loadOrders();
            return response.json();
            
        })
        .then(updatedOrder => {
            console.log('Order and table status updated successfully:', updatedOrder);
        })
        .catch(error => {
            console.error('Error fetching or processing order details:', error);
        });
}

// To display the generated bill
function displayBillModal(billHtml) {
    const billModal = document.getElementById('bill-modal');
    if (!billModal) {
        console.error('Bill modal not found');
        return;
    }
    const billContent = document.getElementById('bill-content');
    if (!billContent) {
        console.error('Bill modal content not found');
        return;
    }
    billContent.innerHTML = billHtml;
    billModal.style.display = 'block';
}

// To close the generated bill modal
function closeBillModal(event) {
    const billModal = document.getElementById('bill-modal');
    billModal.style.display = 'none';
}

// Event listener for the close button
document.querySelector('.close-button').addEventListener('click', closeBillModal);

// Close the modal when clicking outside the content area
window.onclick = function(event) {
    const billModal = document.getElementById('bill-modal');
    if (event.target === billModal) {
        billModal.style.display = 'none';
    }
};
