function calculateRowData() {
        // Loop through each row and calculate the totals
        document.querySelectorAll('.tbody_product tr').forEach(row => {
            const qty = parseFloat(row.querySelector('.qty').value) || 0;
            const unitPrice = parseFloat(row.querySelector('.unit-price').value) || 0;
            const vatPercentage = parseFloat(row.querySelector('.vat').value) || 0;

        // Calculate Total Net, VAT Amount, and Total Gross
            const totalNet = qty * unitPrice;
            const vatAmount = (totalNet * vatPercentage) / 100;
            const totalGross = totalNet + vatAmount;


        // Update values in the row
            row.querySelector('.total-net').textContent = totalNet.toFixed(2);
            row.querySelector('.vat-amount').textContent = vatAmount.toFixed(2);
            row.querySelector('.total-gross').textContent = totalGross.toFixed(2);
        });
    }

    // Automatically calculate when the page loads
        window.onload = function() {
        calculateRowData();
        };