package com.services.dpidcalarm.datainfo.controller;

import com.services.dpidcalarm.base.controller.BaseController;
import com.services.dpidcalarm.base.service.BaseService;
import com.services.dpidcalarm.datainfo.bean.DataInfo;
import com.services.dpidcalarm.datainfo.bean.DataInfoError;
import com.services.dpidcalarm.datainfo.dao.DataInfoDao;
import com.services.dpidcalarm.datainfo.dao.DataInfoErrorDao;
import com.services.dpidcalarm.datainfo.service.DataInfoService;
import com.services.dpidcalarm.utils.AjaxBody;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"data"})
public class DataInfoController extends BaseController<DataInfo>
{

    @Autowired
    private DataInfoService dataInfoService;

    @Autowired
    private DataInfoDao dataInfoDao;

    @Autowired
    private DataInfoErrorDao dataInfoErrorDao;

    @Override
    public BaseService<DataInfo> baseService() {
        return this.dataInfoService;
    }

    @GetMapping
    @Override
    public AjaxBody pageList(DataInfo dataInfo, @RequestParam(value="page", required=false, defaultValue="1") Integer page, @RequestParam(value="rows", required=false, defaultValue="10") Integer pageSize)
    {
        return AjaxBody.success(this.dataInfoService.findUnQualified(dataInfo, page, pageSize));
    }
    @GetMapping({"errors/{dataId}"})
    public AjaxBody errors(@PathVariable("dataId") Integer dataId) {
        return AjaxBody.success(this.dataInfoService.errors(dataId));
    }

    @RequestMapping({"/yearAlarmCount"})
    public Map<String, Object> yearAlarmCount(@RequestParam("years") String years)
    {
        String[] year_arr = years.split(",");
        return this.dataInfoService.monthAvgInfo(year_arr);
    }

    @RequestMapping({"/monthAlarmCount"})
    public Map<String, Object> monthAlarmCount(@RequestParam("dates") String dates)
    {
        String[] datesStr = dates.split(",");
        return this.dataInfoService.dayAvgInfo(datesStr);
    }

    @RequestMapping({"/dayAlarmCount"})
    public Map<String, Object> dayAlarmCount(@RequestParam("dates") String dates)
    {
        String[] datesStr = dates.split(",");
        return this.dataInfoService.minuteInfo(datesStr);
    }

    @GetMapping({"last3DataInfo"})
    public List<DataInfo> last3DataInfo()
    {
        return this.dataInfoService.last3DataInfo();
    }

    @GetMapping({"exportNotHG"})
    public void exportNotHG(String startTimeStr, String endTimeStr, HttpServletResponse response, HttpServletRequest request)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DataInfo dataInfo = new DataInfo();
        dataInfo.setStartTimeStr(startTimeStr);
        dataInfo.setEndTimeStr(endTimeStr);
        List<DataInfo> list = this.dataInfoDao.findUnQualified(dataInfo);
        List result = new ArrayList();
        for (DataInfo dataInfo1 : list) {
            List errors = this.dataInfoErrorDao.findByDataId(dataInfo1.getId());
            result.addAll(errors);
        }
        response.setContentType("application/octet-stream; charset=utf-8");
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        Sheet sheet = sxssfWorkbook.createSheet();
        Row title = sheet.createRow(0);
        Cell dateTitle = title.createCell(0);
        dateTitle.setCellValue("时间:" + startTimeStr + " - " + endTimeStr);
        Row title2 = sheet.createRow(1);
        title2.createCell(0).setCellValue("设备名称");
        title2.createCell(1).setCellValue("量测值");
        title2.createCell(2).setCellValue("估计值");
        title2.createCell(3).setCellValue("设备类型");
        title2.createCell(4).setCellValue("采集时间");
        int len = result.size();
        for (int i = 2; i < len; i++) {
            DataInfoError dataErrorInfo = (DataInfoError)result.get(i - 2);
            Row row = sheet.createRow(i);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(dataErrorInfo.getDeviceName());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(dataErrorInfo.getTestValue().floatValue());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(dataErrorInfo.getEstimateValue().floatValue());
            Cell cell4 = row.createCell(3);
            cell4.setCellValue(dataErrorInfo.getDeviceType());
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(sdf.format(dataErrorInfo.getCreateTime()));
        }
        sxssfWorkbook.setSheetName(0, "设备错误明细");
        Sheet sheet2 = sxssfWorkbook.createSheet();
        Row sheet2Title = sheet2.createRow(0);
        sxssfWorkbook.setSheetName(1, "错误设备统计");
        sheet2Title.createCell(0).setCellValue("设备名称");
        sheet2Title.createCell(1).setCellValue("错误次数");
        List elist = this.dataInfoDao.dataInfoErrorSum(startTimeStr, endTimeStr);
        int elen = elist.size();
        for (int i = 0; i < elen; i++) {
            Map map = (Map)elist.get(i);
            Row row = sheet2.createRow(i + 1);
            String deviceName = map.get("device_name").toString();
            String c = map.get("c").toString();
            row.createCell(0).setCellValue(deviceName);
            row.createCell(1).setCellValue(c);
        }
        String tab = "不合格率数据统计";
        try {
            String userAgent = request.getHeader("User-Agent");
            if ((userAgent.contains("MSIE")) || (userAgent.contains("Trident"))) {
                tab = URLEncoder.encode(tab, "UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=" + tab + ".xlsx");
            } else {
                tab = new String(tab.getBytes("UTF-8"), "ISO-8859-1");
                response.setHeader("Content-Disposition", "attachment; filename=" + tab + ".xlsx");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            sxssfWorkbook.write(out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping({"/deviceErrorSum"})
    public List<Map<String, Object>> deviceErrorSum(String startTimeStr, String endTimeStr) {
        return this.dataInfoDao.dataInfoErrorSum(startTimeStr, endTimeStr);
    }

    @RequestMapping({"/exportYearAlarmExcel"})
    public void exportYearAlarmExcel(@RequestParam("years") String years, @RequestParam("type") Integer type, HttpServletRequest request, HttpServletResponse response)
    {
        String[] years_str = years.split(",");
        this.dataInfoService.createExcelFile(years_str, type, request, response);
    }
}