

    document.addEventListener('DOMContentLoaded', function () {

        const recurrenceDiv = document.getElementById("recurrenceInvoice");
        const kindSelect = document.getElementById('kind');
        const isRecurring = document.getElementById('isRecurring');
        kindSelect.addEventListener('change', function(){
            if(kindSelect.options[kindSelect.selectedIndex].text=="Recurrence Invoice"){
                recurrenceDiv.style.display = "flex";
                isRecurring.value="true";
                document.querySelectorAll('#recurrenceInvoice input, #recurrenceInvoice select').forEach(input => {
                   input.setAttribute('required', 'true')
               });
            }else{
                recurrenceDiv.style.display = "none";
                isRecurring.value="false";
                 document.querySelectorAll('#recurrenceInvoice input, #recurrenceInvoice select').forEach(input => {
                    input.removeAttribute('required');
                });
            }
        })
//        if (checkbox) {
//            // Use `change` event for checkbox toggle
//            checkbox.addEventListener("change", () => {
//                if (recurrenceDiv.style.display === "none" || recurrenceDiv.style.display === "") {
//                    recurrenceDiv.style.display = "block";
//                } else {
//                    recurrenceDiv.style.display = "none";
//                }
//            });
//        }




        const today = new Date().toISOString().split('T')[0];
        document.getElementById('invoiceDate').value = today;



         //------adjust paid field follwing state filed---------

//        //Get element select
//        var e = document.getElementById("status");
//        //Get element paid
//        var paidEl = document.getElementById("paid");
//        //Get total gross
//        var paidVal = document.getElementById("grossTotal").value;
//        console.log(e);
//        console.log(paidEl);
//        console.log(paidVal);
//        console.log('testing');
//
//       e.addEventListener("change", function() {
//            var value = e.value;
//            var text2 = e.options[e.selectedIndex].text;
//            if (text2 === "Paid") {
//                paidEl.value = grossTotal;
//            } else {
//                paidEl.value = 0;
//            }
//        });

        const addItemBtn = document.getElementById('addItemBtn');
        const invoiceTable = document.getElementById('invoiceTable').getElementsByTagName('tbody')[0];


        const productSelect = document.querySelector('.product-select');
        const options = productSelect.querySelectorAll('option');
        const products2 = Array.from(options).map(option => {
            return {
                id: option.value,
                name: option.textContent,
                unit: option.dataset.unit,
                price: option.dataset.price,
                vat: option.dataset.vat };
            }).filter(product => product.id); // Remove the default option console.log(products);

        console.log(products2)




        // Add new row
        addItemBtn.addEventListener('click', function () {
            const newRow = invoiceTable.insertRow();

            newRow.innerHTML = `
                <td>
                    <select class="form-control product-select" name="productId" >
                        <option value="">-- Chọn sản phẩm --</option>
                        <!-- Assuming products are globally available via a variable -->
                        ${products2.map(product => `
                            <option value="${product.id}"
                                    data-unit="${product.unit}"
                                    data-price="${product.price}"
                                    data-vat="${product.vat}">
                                ${product.name}
                            </option>`).join('')}
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
           const productSelect = newRow.querySelector('.product-select');
           productSelect.addEventListener('change', function() {
            const selectedOption = this.options[this.selectedIndex];
            newRow.querySelector('.item-unit').value = selectedOption.dataset.unit;
            newRow.querySelector('.item-price').value = selectedOption.dataset.price;
            newRow.querySelector('.item-vat').value = selectedOption.dataset.vat;
            updateTotals();
    });
            // Bind events for newly added row
            bindEvents();
        });

        function validateForm() {
            const tbody = document.querySelector('tbody');
            const rows = tbody.querySelectorAll('tr');

            // Kiểm tra nếu tbody không có hàng
            if (rows.length === 0) {
                alert('Please add at least one "Product" before submitting.');
                return false; // Ngăn form gửi đi
            }

            // Lặp qua tất cả các hàng để kiểm tra giá trị của các ô select
            for (let i = 0; i < rows.length; i++) {
                const select = rows[i].querySelector('.product-select'); // Tìm select trong hàng hiện tại
                if (!select || select.value === "") {
                    alert(`Please select a product in row ${i + 1}!`);
                    return false; // Ngăn form gửi đi
                }
            }

            // Nếu tất cả đều ổn, cho phép gửi form
            return true;
        }
        function validateForm2() {
            const paymentTimeInput = document.getElementById('paymentTime');
            const today = new Date();
             // Định dạng ngày thành yyyy-mm-dd
             const todayFormatted = today.toISOString().split('T')[0];
             const selectedDate = new Date(paymentTimeInput.value);
             const currentDate = new Date(todayFormatted);

             if (selectedDate < currentDate) {
                    alert('"Payment Time" must be today or a future date.');
                    return false;
             }
             return true;
        }
        function validateForm3() {
               const select = document.querySelector('.department-select');
               if (!select || select.value === "") {
                   alert(`Please select Seller`);
                   return false; // Ngăn form gửi đi
               }
               return true;
        }

        function validateForm4() {
               const select = document.querySelector('.vendor-select');
               if (!select || select.value === "") {
                    alert(`Please select Buyer`);
                    return false; // Ngăn form gửi đi
                    }
               return true;
        }

        function validateForm5() {
               const total = document.getElementById('grossTotal');
               const paid = document.getElementById('paid');
               if (paid.value > total.value) {
                   alert(`The payment amount is invalid. Please enter a valid amount.`);
                   return false; // Ngăn form gửi đi
               }
               return true;
        }

        document.querySelector('form').addEventListener('submit', function (event) {
                    if (!validateForm() || !validateForm2() || !validateForm3() || !validateForm4() || !validateForm5()) {
                        event.preventDefault(); // Ngăn việc gửi form nếu không hợp lệ
                    }
        });

        // Re-bind event listeners to rows
        function bindEvents() {
            // Handle deletion of rows
            const deleteButtons = document.querySelectorAll('.deleteRow');
            deleteButtons.forEach(function (button) {
                button.addEventListener('click', function (e) {
                    const row = e.target.closest('tr');
                    row.remove();
                    updateTotals(); // Update totals after removing row
                });
            });

            // Handle input changes
            const quantityInputs = document.querySelectorAll('.item-quantity');
            const priceInputs = document.querySelectorAll('.item-price');
            const vatInputs = document.querySelectorAll('.item-vat');

            quantityInputs.forEach(function (input) {
                input.addEventListener('input', updateTotals);
            });


            document.querySelector('.department-select').addEventListener('change', function() {
                console.log("get in");
                var selectedOption = this.options[this.selectedIndex];
                document.querySelector('.departmentVat').value = selectedOption.dataset.tax;
                document.querySelector('.departmentEmail').value = selectedOption.dataset.email;
                document.querySelector('.departmentPostcode').value = selectedOption.dataset.postcode;
                document.querySelector('.departmentStreet').value = selectedOption.dataset.street;
                document.querySelector('.departmentCity').value = selectedOption.dataset.city;
                document.querySelector('.departmentAcc').value = selectedOption.dataset.bankacc;
                document.querySelector('.departmentBankName').value = selectedOption.dataset.bank;
                document.querySelector('.departmentEmail').value = selectedOption.dataset.email;
            });



            document.querySelector('.vendor-select').addEventListener('change', function() {
                var selectedOption = this.options[this.selectedIndex];
                document.querySelector('.bankAccount').value = selectedOption.dataset.bankaccount;
                document.querySelector('.bank').value = selectedOption.dataset.bank;
                document.querySelector('.email').value = selectedOption.dataset.email;
                document.querySelector('.buyer-postcode').value = selectedOption.dataset.postcode;
                document.querySelector('.buyer-street').value = selectedOption.dataset.street;
                document.querySelector('.buyer-city').value = selectedOption.dataset.city;
                document.querySelector('.buyer-tax').value = selectedOption.dataset.tax;
            });



            document.querySelector('.product-select').addEventListener('change', function() {
                var selectedOption = this.options[this.selectedIndex];
                document.querySelector('.item-unit').value = selectedOption.dataset.unit;
                document.querySelector('.item-price').value = selectedOption.dataset.price;
                document.querySelector('.item-vat').value = selectedOption.dataset.vat;
                updateTotals();
            });
        }

        // Calculate totals
        function updateTotals() {
            let netTotal = 0;
            let vatTotal = 0;
            let grossTotal = 0;

            const rows = document.querySelectorAll('#invoiceTable tbody tr');
            rows.forEach(function (row) {
                const quantity = parseFloat(row.querySelector('.item-quantity').value) || 0;
                const price = parseFloat(row.querySelector('.item-price').value) || 0;
                const vat = parseFloat(row.querySelector('.item-vat').value) || 0;

                const totalNet = quantity * price;
                const totalGross = totalNet * (1 + vat / 100);

                row.querySelector('.total-net').value = totalNet.toFixed(2);
                row.querySelector('.total-gross').value = totalGross.toFixed(2);

                netTotal += totalNet;
                vatTotal += totalNet * (vat / 100);
                grossTotal += totalGross;
            });

            document.getElementById('netTotal').value=  parseFloat(netTotal.toFixed(2));
            document.getElementById('vatTotal').value=  parseFloat(vatTotal.toFixed(2));
            document.getElementById('grossTotal').value= parseFloat(grossTotal.toFixed(2));
        }

        // Initialize event listeners
        bindEvents();
    });
