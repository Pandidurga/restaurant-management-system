/* 
    This script handles the dynamic behavior of the menu item management interface.
    It interacts with the backend API to perform CRUD operations on menu items.
*/

document.addEventListener('DOMContentLoaded', function() {
    
    // Base URL for API endpoints
    const apiUrl = 'http://localhost:8083/api/menu-items';
    
    // DOM elements
    const addButton = document.getElementById('addButton');
    const addItemModal = document.getElementById('addItemModal');
    const editItemModal = document.getElementById('editItemModal');
    const closeAddModal = document.querySelector('#addItemModal .close');
    const closeEditModal = document.querySelector('#editItemModal .close');
    const addItemForm = document.getElementById('addItemForm');
    const editItemForm = document.getElementById('editItemForm');
    
    // Event listener for adding a new menu item
    addButton.addEventListener('click', function() {
        addItemModal.style.display = 'block'; // Show add item modal
    });

    // Event listener to close add item modal
    closeAddModal.addEventListener('click', function() {
        addItemModal.style.display = 'none'; // Hide add item modal
        resetAddForm(); // Reset form inputs
    });

    // Event listener to close edit item modal
    closeEditModal.addEventListener('click', function() {
        editItemModal.style.display = 'none'; // Hide edit item modal
    });

    // Event listener to close modals on click outside modal
    window.addEventListener('click', function(event) {
        if (event.target == addItemModal) {
            addItemModal.style.display = 'none'; // Hide add item modal
        } else if (event.target == editItemModal) {
            editItemModal.style.display = 'none'; // Hide edit item modal
        }
    });
    
    // Function to reset add item form
    function resetAddForm() {
        document.getElementById('itemName').value = '';
        document.getElementById('itemPrice').value = '';
        document.getElementById('availability').checked = false;
    }
    
    // Function to fetch menu items from the API and populate the table
    function fetchMenuItems() {
        fetch(`${apiUrl}/get-all`)
            .then(response => response.json())
            .then(menuItems => {
                const menuItemsList = document.getElementById('menuItemsList');
                menuItemsList.innerHTML = ''; // Clear existing rows

                // Populate table with menu items
                menuItems.forEach(menuItem => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${menuItem.menuItemId}</td>
                        <td>${menuItem.itemName}</td>
                        <td>${menuItem.unitPrice}</td>
                        <td>${menuItem.availability ? 'Available' : 'Not Available'}</td>
                        <td>
                            <button class="editBtn" data-id="${menuItem.menuItemId}">Edit</button>
                            <button class="deleteBtn" data-id="${menuItem.menuItemId}">Delete</button>
                        </td>
                    `;
                    menuItemsList.appendChild(row);
                });

                // Add event listeners for edit/delete buttons
                addEditDeleteListeners();
            })
            .catch(error => console.error('Error fetching menu items:', error));
    }

    // Call fetchMenuItems function to populate the table initially
    fetchMenuItems();

    // Function to add event listeners for edit/delete buttons
    function addEditDeleteListeners() {
        const editButtons = document.querySelectorAll('.editBtn');
        const deleteButtons = document.querySelectorAll('.deleteBtn');

        // Event listener for edit button
        editButtons.forEach(button => {
            button.addEventListener('click', function() {
                const id = this.getAttribute('data-id');
                // Fetch menu item details by ID and populate edit modal
                fetch(`${apiUrl}/get-by-id/${id}`)
                    .then(response => response.json())
                    .then(menuItem => {
                        document.getElementById('editItemId').value = menuItem.menuItemId;
                        document.getElementById('editItemName').value = menuItem.itemName;
                        document.getElementById('editItemPrice').value = menuItem.unitPrice;
                        document.getElementById('editAvailability').checked = menuItem.availability;
                        editItemModal.style.display = 'block'; // Show edit item modal
                    })
                    .catch(error => console.error('Error fetching menu item details:', error));
            });
        });

        // Event listener for delete button
        deleteButtons.forEach(button => {
            button.addEventListener('click', function() {
                const id = this.getAttribute('data-id');
                if (confirm('Are you sure you want to delete this menu item?')) {
                    // Send DELETE request to delete menu item by ID
                    fetch(`${apiUrl}/delete/${id}`, {
                        method: 'DELETE'
                    })
                    .then(response => {
                        if (response.ok) {
                            fetchMenuItems(); // Refresh table
                            alert('Menu item deleted successfully');
                        } else {
                            alert('Failed to delete menu item');
                        }
                    })
                    .catch(error => console.error('Error deleting menu item:', error));
                }
            });
        });
    }

    // Event listener for form submission to add a new menu item
    addItemForm.addEventListener('submit', function(event) {
        event.preventDefault();
        // Get form input values
        const itemName = document.getElementById('itemName').value;
        const unitPrice = document.getElementById('itemPrice').value;
        const availability = document.getElementById('availability').checked;

        const newItem = {
            itemName: itemName,
            unitPrice: unitPrice,
            availability: availability
        };

        // Send POST request to add new menu item
        fetch(`${apiUrl}/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newItem)
        })
        .then(response => {
            if (response.ok) {
                fetchMenuItems(); // Refresh table
                addItemModal.style.display = 'none'; // Hide add item modal
                alert('Menu item added successfully');
                resetAddForm(); // Reset form inputs
            } else {
                alert('Failed to add menu item...Because it already exists');
            }
        })
        .catch(error => console.error('Error adding menu item:', error));
    });

    // Event listener for form submission to edit a menu item
    editItemForm.addEventListener('submit', function(event) {
        event.preventDefault();
        // Get form input values
        const id = document.getElementById('editItemId').value;
        const name = document.getElementById('editItemName').value;
        const price = document.getElementById('editItemPrice').value;
        const availability = document.getElementById('editAvailability').checked;

        const updatedItem = {
            itemName: name,
            unitPrice: price,
            availability: availability
        };

        // Send PUT request to update menu item by ID
        fetch(`${apiUrl}/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedItem)
        })
        .then(response => {
            if (response.ok) {
                fetchMenuItems(); // Refresh table
                editItemModal.style.display = 'none'; // Hide edit item modal
                alert('Menu item updated successfully');
            } else {
                alert('Failed to update menu item');
            }
        })
        .catch(error => console.error('Error updating menu item:', error));
    });
   
});
