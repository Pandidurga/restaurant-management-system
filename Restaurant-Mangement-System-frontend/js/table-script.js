// Wait for the DOM content to load before executing JavaScript
document.addEventListener("DOMContentLoaded", () => {
    // Base URL for the API endpoints
    const apiUrl = "http://localhost:8083/api/restaurant-tables"; 

    // DOM elements
    const tablesList = document.getElementById("tablesList");
    const addButton = document.getElementById("addButton");
    const addTableModal = document.getElementById("addTableModal");
    const editTableModal = document.getElementById("editTableModal");

    const addTableForm = document.getElementById("addTableForm");
    const editTableForm = document.getElementById("editTableForm");

    // Function to close a modal
    const closeModal = (modal) => {
        modal.style.display = "none";
    };

    // Function to open a modal
    const openModal = (modal) => {
        resetAddTableForm();
        modal.style.display = "block";
    };

    // Function to fetch and display tables from the backend
    const getTables = async () => {
        try {
            const response = await fetch(`${apiUrl}/get-all`);
            const tables = await response.json();
            renderTables(tables);
        } catch (error) {
            console.error("Error fetching tables:", error);
            alert("Error fetching tables.");
        }
    };

    // Function to render tables in the HTML table
    const renderTables = (tables) => {
        tablesList.innerHTML = ""; // Clear existing rows
        tables.forEach(table => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${table.tableId}</td>
                <td>${table.tableNumber}</td>
                <td>${table.seats}</td>
                <td>${table.status}</td>
                <td>
                    <button class="editButton" data-id="${table.tableId}">Edit</button>
                    <button class="deleteButton" data-id="${table.tableId}">Delete</button>
                </td>
            `;
            tablesList.appendChild(row); // Append row to the table body
        });
    };

    // Function to add a new table
    const addTable = async (table) => {
        try {
            const response = await fetch(`${apiUrl}/add`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(table)
            });
            if (response.ok) {
                alert("Table added successfully.");
                getTables(); // Refresh the table list
            } else {
                throw new Error("Failed to add table. It already exists.");
            }
        } catch (error) {
            console.error("Error adding table:", error);
            alert("Error adding table.");
        }
    };

    // Function to edit an existing table
    const editTable = async (table) => {
        try {
            const response = await fetch(`${apiUrl}/update/${table.tableId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(table)
            });
            if (response.ok) {
                alert("Table updated successfully.");
                getTables(); // Refresh the table list
            } else {
                throw new Error("Failed to update table.");
            }
        } catch (error) {
            console.error("Error editing table:", error);
            alert("Error editing table.");
        }
    };

    // Function to delete a table
    const deleteTable = async (id) => {
        try {
            const response = await fetch(`${apiUrl}/delete/${id}`, {
                method: "DELETE"
            });
            if (response.ok) {
                alert("Table deleted successfully.");
                getTables(); // Refresh the table list
            } else {
                throw new Error("Failed to delete table.");
            }
        } catch (error) {
            console.error("Error deleting table:", error);
            alert("Error deleting table.");
        }
    };

    // Event listener for the "Add New Table" button
    addButton.addEventListener("click", () => {
        openModal(addTableModal); // Open the add table modal
    });

    // Event listener to close the add table modal
    addTableModal.querySelector(".close").addEventListener("click", () => {
        closeModal(addTableModal);
    });

    // Event listener to close the edit table modal
    editTableModal.querySelector(".close").addEventListener("click", () => {
        closeModal(editTableModal);
    });

    // Event listener for the add table form submission
    addTableForm.addEventListener("submit", (e) => {
        e.preventDefault(); // Prevent default form submission
        const table = {
            tableNumber: document.getElementById("tableNumber").value,
            seats: document.getElementById("seats").value,
            status: document.getElementById("status").value
        };
        addTable(table); // Add the new table
        closeModal(addTableModal); // Close the modal
    });

    // Event listener for edit and delete buttons in the table list
    tablesList.addEventListener("click", (e) => {
        if (e.target.classList.contains("editButton")) {
            const id = e.target.dataset.id;
            fetch(`${apiUrl}/get-by-id/${id}`)
                .then(response => response.json())
                .then(table => {
                    
                    // Populate the edit form with table data
                    document.getElementById("editTableId").value = table.tableId;
                    document.getElementById("editTableNumber").value = table.tableNumber;
                    document.getElementById("editSeats").value = table.seats;
                    document.getElementById("editStatus").value = table.status;
                    openModal(editTableModal); // Open the edit table modal
                })
                .catch(error => {
                    console.error("Error fetching table:", error);
                    alert("Error fetching table details.");
                });
        }

        if (e.target.classList.contains("deleteButton")) {
            const id = e.target.dataset.id;
            if (confirm("Are you sure you want to delete this table?")) {
                deleteTable(id); // Delete the table
            }
        }
    });

    // Event listener for the edit table form submission
    editTableForm.addEventListener("submit", (e) => {
        e.preventDefault(); // Prevent default form submission
        const table = {
            tableId: document.getElementById("editTableId").value,
            tableNumber: document.getElementById("editTableNumber").value,
            seats: document.getElementById("editSeats").value,
            status: document.getElementById("editStatus").value
        };
        editTable(table); // Edit the table
        closeModal(editTableModal); // Close the modal
    });
    
    // Function to reset the add table form
    const resetAddTableForm = () => {
        document.getElementById("tableNumber").value = "";
        document.getElementById("seats").value = "";
        document.getElementById("status").value = "";
    };
    // Initial fetch and render of tables
    getTables();
});
