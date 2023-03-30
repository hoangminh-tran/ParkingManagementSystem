package com.demo.utils.excel;

import com.demo.utils.request.StatisticDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportConfig {
    private int sheetIndex;

    private int startRow;

    private Class dataClass;

    private List<CellConfig> cellExportConfigs;

    public static final ExportConfig exportConfig;

    static{
        exportConfig = new ExportConfig();
        exportConfig.setSheetIndex(0);
        exportConfig.setStartRow(1);
        exportConfig.setDataClass(StatisticDTO.class);

        List<CellConfig> list = new ArrayList<>();
        list.add(new CellConfig(0, "startDate"));
        list.add(new CellConfig(1, "endDate"));
        list.add(new CellConfig(2, "totalOfMoney"));
        list.add(new CellConfig(3, "numberOfCustomerInvoice"));
        list.add(new CellConfig(4, "numberOfResidentInvoice"));
        list.add(new CellConfig(5, "numberOfCustomerInvoiceExpire"));
        list.add(new CellConfig(6, "numberOfResidentInvoiceExpire"));

        exportConfig.setCellExportConfigs(list);
    }
}
