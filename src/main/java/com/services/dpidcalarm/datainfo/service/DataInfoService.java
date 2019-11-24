package com.services.dpidcalarm.datainfo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.base.bean.MyMapper;
import com.services.dpidcalarm.base.service.BaseServiceAdapter;
import com.services.dpidcalarm.datainfo.bean.DataInfo;
import com.services.dpidcalarm.datainfo.bean.DataInfoError;
import com.services.dpidcalarm.datainfo.dao.DataInfoDao;
import com.services.dpidcalarm.datainfo.dao.DataInfoErrorDao;
import com.services.dpidcalarm.utils.CommonUtils;
import com.services.dpidcalarm.utils.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Service
@Transactional
public class DataInfoService extends BaseServiceAdapter<DataInfo>
{
    public static final int YEAR = 1;
    public static final int YEAR_MONTH = 2;
    public static final int YEAR_MONTH_DAY = 3;

    @Autowired
    private DataInfoDao dataInfoDao;

    @Autowired
    private DataInfoErrorDao dataInfoErrorDao;

    @Override
    public MyMapper<DataInfo> getBaseMapper()
    {
        return this.dataInfoDao;
    }

    @Transactional(readOnly=true)
    public PageInfo<DataInfo> findUnQualified(DataInfo dataInfo, Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum.intValue(), pageSize.intValue());
        List list = this.dataInfoDao.findUnQualified(dataInfo);
        return new PageInfo(list);
    }
    @Transactional(readOnly=true)
    public List<DataInfoError> errors(Integer dataId) {
        return this.dataInfoErrorDao.findByDataId(dataId);
    }
    @Transactional(readOnly=true)
    public Map<String, Object> monthAvgInfo(String[] years) {
        Map result = new HashMap();
        List footer = new ArrayList();
        List resultList = new ArrayList();
        Integer[] years_int = new Integer[years.length];
        for (int i = 0; i < years_int.length; i++) {
            years_int[i] = Integer.valueOf(Integer.parseInt(years[i]));
        }
        int yearLen = years.length;
        for (int j = 0; j < yearLen; j++) {
            Map dataMap = new HashMap();
            List list = this.dataInfoDao.monthAvgInfo(years_int[j]);
            List values = new ArrayList();
            int len = list.size();
            for (int i = 0; i < len; i++) {
                Map map = (Map)list.get(i);
                values.add(Float.valueOf(map.get("Y").toString()));
                if (j != 0) continue; footer.add(map.get("X").toString());
            }
            dataMap.put("name", years[j]);
            dataMap.put("value", values);
            dataMap.put("min", CommonUtils.getMin(values));
            resultList.add(dataMap);
        }
        result.put("footer", footer);
        result.put("data", resultList);
        return result;
    }

    public Map<String, Object> dayAvgInfo(String[] dates) {
        Map result = new HashMap();
        List footer = new ArrayList();
        List resultList = new ArrayList();
        List integers = new ArrayList();
        for (String date : dates) {
            Integer year = CommonUtils.getYear(date);
            Integer month = CommonUtils.getMonth(date);
            Integer days = Integer.valueOf(CommonUtils.getDaysOfMonth(year, month));
            integers.add(days);
        }
        int maxInt = CommonUtils.getMaxInt(integers);
        for (int i = 0; i < maxInt; i++) {
            footer.add(i + 1 + "日");
        }
        for (int j = 0; j < dates.length; j++) {
            Integer year = CommonUtils.getYear(dates[j]);
            Integer month = CommonUtils.getMonth(dates[j]);
            Integer days = Integer.valueOf(CommonUtils.getDaysOfMonth(year, month));
            Map dataMap = new HashMap();
            List list = this.dataInfoDao.dayAvgInfo(year, month);
            list = sureDay(list, year, month);
            List values = new ArrayList();
            int len = list.size();
            for (int i = 0; (i < len) && (i < days.intValue()); i++) {
                Map map = (Map)list.get(i);
                values.add(Float.valueOf(map.get("Y").toString()));
            }
            dataMap.put("name", dates[j]);
            dataMap.put("value", values);
            dataMap.put("min", CommonUtils.getMin(values));
            resultList.add(dataMap);
        }
        result.put("footer", footer);
        result.put("data", resultList);
        return result;
    }
    private List<Map<String, Object>> sureDay(List<Map<String, Object>> list, Integer year, Integer month) {
        List result = new ArrayList();
        Calendar c = Calendar.getInstance();
        c.set(1, year.intValue());
        c.set(2, month.intValue() - 1);
        c.set(5, 1);
        c.set(5, 1);
        c.set(11, 1);
        c.set(12, 1);
        c.set(13, 1);
        Date d = new Date();
        d.setTime(c.getTimeInMillis());
        int dayCount = CommonUtils.getCurrentMonthDay(d);
        for (Map map : list) {
            Integer day = Integer.valueOf(Integer.parseInt(map.get("X").toString()));
            if (day.intValue() <= dayCount) result.add(map);
        }
        return result;
    }
    public Map<String, Object> minuteInfo(String[] dates) {
        Map result = new HashMap();
        List footer = new ArrayList();
        List resultList = new ArrayList();
        int dateLen = dates.length;
        for (int j = 0; j < dateLen; j++) {
            Map dataMap = new HashMap();
            Integer year = CommonUtils.getYear(dates[j]);
            Integer month = CommonUtils.getMonth(dates[j]);
            Integer day = CommonUtils.getDay(dates[j]);
            List list = this.dataInfoDao.minuteInfo(year, month, day);
            List values = new ArrayList();
            int len = list.size();
            for (int i = 0; i < len; i++) {
                Map map = (Map)list.get(i);
                values.add(Float.valueOf(map.get("Y").toString()));
                if (j != 0) continue; footer.add(map.get("X").toString());
            }
            dataMap.put("name", dates[j]);
            dataMap.put("value", values);
            dataMap.put("min", CommonUtils.getMin(values));
            resultList.add(dataMap);
        }
        result.put("footer", footer);
        result.put("data", resultList);
        return result;
    }

    public List<DataInfo> last3DataInfo()
    {
        List<DataInfo> dataInfos = this.dataInfoDao.last3DataInfo();
        for (DataInfo dataInfo : dataInfos) {
            dataInfo.setErrors(this.dataInfoErrorDao.findByDataId(dataInfo.getId()));
        }
        return dataInfos;
    }

    public void createExcelFile(String[] dates, Integer type, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        String tab = null;
        int type_base = type.intValue();
        switch (type_base) {
            case 1:
                tab = "年度报警信息";
                break;
            case 2:
                tab = "月度报警信息";
                break;
            case 3:
                tab = "每日报警信息";
        }

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        for (int i = 0; i < dates.length; i++) {
            Sheet sheet = sxssfWorkbook.createSheet();
            Row title = sheet.createRow(0);
            Cell dateTitle = title.createCell(0);
            dateTitle.setCellValue("时间");
            Cell valueTitle = title.createCell(1);
            valueTitle.setCellValue("单独合格率");
            List list = null;
            switch (type_base) {
                case 1:
                    list = this.dataInfoDao.monthAvgInfo(Integer.valueOf(Integer.parseInt(dates[i])));
                    break;
                case 2:
                    list = this.dataInfoDao.dayAvgInfo(CommonUtils.getYear(dates[i]), CommonUtils.getMonth(dates[i]));
                    list = filterMonth(CommonUtils.getYear(dates[i]), CommonUtils.getMonth(dates[i]), list);
                    break;
                case 3:
                    list = this.dataInfoDao.minuteInfo(CommonUtils.getYear(dates[i]), CommonUtils.getMonth(dates[i]), CommonUtils.getDay(dates[i]));
            }
            for (int j = 1; j < list.size() + 1; j++) {
                Map map = (Map)list.get(j - 1);
                Row row = sheet.createRow(j);
                Cell dateCell = row.createCell(0);
                dateCell.setCellValue(dates[i] + " - " + map.get("X").toString());
                Cell valueCell = row.createCell(1);
                valueCell.setCellValue(map.get("Y").toString());
            }
            sxssfWorkbook.setSheetName(i, String.valueOf(dates[i]));
        }
        try {
            String userAgent = request.getHeader("User-Agent");
            if ((userAgent.contains("MSIE")) || (userAgent.contains("Trident"))){
                tab = URLEncoder.encode(tab, "UTF-8");
            }
            else {
                tab = new String(tab.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.setHeader("Content-Disposition", "attachment; filename=" + tab + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        FileOutputStream fos = null;
        File file = new File("temp.xlsx");
        try {
            fos = new FileOutputStream(file);
            sxssfWorkbook.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileUtils.download(file, response);
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Map<String, Object>> filterMonth(Integer year, Integer month, List<Map<String, Object>> list) {
        List result = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year.intValue());
        calendar.set(2, month.intValue() - 1);
        Integer maxDay = Integer.valueOf(calendar.getActualMaximum(5));
        for (Map map : list) {
            Integer m = Integer.valueOf(Integer.parseInt(map.get("X").toString()));
            if (m.intValue() <= maxDay.intValue()) {
                result.add(map);
            }
        }
        return result;
    }
}