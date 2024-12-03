// Listen for the click event on the button
const button = document.getElementById('but');

// Add a click event listener
button.addEventListener('click', function() {

    alert("testiong")
    // Create a new table row
    const newRow = document.createElement("tr");

    newRow.innerHTML = `
        <td>
            <select class="form-control product-select" name="productId">
                <option value="">-- Chọn sản phẩm --</option>
                <th:block th:each="product : ${products}">
                    <option value="${product.id}"
                            text="${product.name}"
                            th:attr="data-unit=${product.unit},data-price=${product.price},data-vat=${product.tax}"></option>
                </th:block>
            </select>
        </td>
        <td><input type="number" class="form-control item-quantity" name="quantities" value="1" min="1"></td>
        <td><input type="text" class="form-control item-unit" disabled></td>
        <td><input type="number" class="form-control item-price" disabled></td>
        <td><input type="number" class="form-control item-vat" disabled></td>
        <td><input type="number" class="form-control total-net" disabled></td>
        <td><input type="number" class="form-control total-gross" disabled></td>
        <td><button class="btn btn-danger btn-sm deleteRow"><i class="bi bi-x"></i></button></td>
    `;

    // Find the table body and append the new row to it
    const tableBody = document.querySelector("#invoiceTable tbody");
    tableBody.appendChild(newRow);

    // Add event listener to the delete button in the new row
    newRow.querySelector(".deleteRow").addEventListener("click", function() {
        newRow.remove();
    });
});

// Event listener for product selection
document.addEventListener('change', function(e) {
    if (e.target.classList.contains('product-select')) {
        const selectedOption = e.target.options[e.target.selectedIndex];
        const unit = selectedOption.getAttribute('data-unit');
        const price = selectedOption.getAttribute('data-price');
        const vat = selectedOption.getAttribute('data-vat');

        const row = e.target.closest('tr');
        row.querySelector('.item-unit').value = unit;
        row.querySelector('.item-price').value = price;
        row.querySelector('.item-vat').value = vat;
    }
});
