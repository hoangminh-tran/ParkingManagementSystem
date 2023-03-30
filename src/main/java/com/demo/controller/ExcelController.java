package com.demo.controller;

import com.demo.service.StatisticInvoiceService;
import com.demo.utils.excel.ExcelConfig;
import com.demo.utils.request.StatisticDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelController {

    private final StatisticInvoiceService statisticInvoiceService;
    @CrossOrigin(origins = "http://localhost:6969")
    @GetMapping("/export")
    public ResponseEntity<Resource> exportCustomerInvoice(@RequestParam("time") String time) throws Exception
    {
        List<StatisticDTO> statisticList = statisticInvoiceService.ImportStatisticInvoice(time);
        for(StatisticDTO dto : statisticList)
        {
            log.info(dto + "");
        }
        if(!CollectionUtils.isEmpty(statisticList))
        {
            String fileName = "Statistic Invoice Export" + ".xlsx";

            ByteArrayInputStream inputStream = ExcelConfig.exportCustomer(statisticList, fileName);

            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename='" + URLEncoder.encode(fileName, StandardCharsets.UTF_8))
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel; charset=UTF-8"))
                    .body(inputStreamResource);
        }
        else {
            throw new Exception("No data");
        }
    }
}
