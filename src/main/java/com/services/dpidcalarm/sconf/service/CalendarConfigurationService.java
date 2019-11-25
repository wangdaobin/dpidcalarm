// package com.services.dpidcalarm.sconf.service;
//
// import com.services.dpidcalarm.base.bean.MyMapper;
// import com.services.dpidcalarm.base.service.BaseServiceAdapter;
// import com.services.dpidcalarm.sconf.bean.CalendarConfiguration;
// import com.services.dpidcalarm.sconf.dao.CalendarConfigurationDao;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
//
// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.List;
//
// @Service
// @Transactional
// public class CalendarConfigurationService extends BaseServiceAdapter<CalendarConfigurationService>
// {
//
//     @Autowired
//     private CalendarConfigurationDao calendarConfigurationDao;
//
//
//     @Override
//     public MyMapper<CalendarConfiguration> getBaseMapper() {
//     return (MyMapper<CalendarConfiguration>)(this.calendarConfigurationDao);
//     }
//
//
//
//     @Scheduled(cron="0 0 0 1 11 * ?")
//     private void createCalendar() throws Exception {
//         Calendar calendar = Calendar.getInstance();
//         int year = Calendar.getInstance().get(1);
//         int month = 12;
//         calendar.clear();
//         calendar.set(1, year);
//         List records = new ArrayList();
//         for (int j = 0; j < month; j++) {
//             calendar.set(2, j);
//             int days = calendar.getActualMaximum(5);
//             for (int k = 1; k <= days; k++) {
//                 CalendarConfiguration cc = new CalendarConfiguration(Integer.valueOf(year), Integer.valueOf(j + 1), Integer.valueOf(k), Integer.valueOf(0));
//                 records.add(cc);
//             }
//         }
//         this.calendarConfigurationDao.insertList(records);
//     }
// }