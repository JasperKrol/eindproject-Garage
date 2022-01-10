package com.eqriesracingteam.garage.dto;

import com.eqriesracingteam.garage.model.Invoice;

import java.math.BigDecimal;

public class InvoiceDto {

    public long invoiceNumber;
    public BigDecimal grossAmount;
    public BigDecimal vatAmount;
    public BigDecimal nettoAmount;
    public boolean invoicePaid;
    // TODO: 10-1-2022 relations in dto zetten

    public static InvoiceDto fromInvoice(Invoice invoice){
        var dto = new InvoiceDto();

        dto.invoiceNumber = invoice.getInvoiceNumber();
        dto.grossAmount = invoice.getGrossAmount();
        dto.vatAmount = invoice.getVatAmount();
        dto.nettoAmount = invoice.getNettoAmount();
        dto.invoicePaid = invoice.isInvoicePaid();

        return dto;
    }
}