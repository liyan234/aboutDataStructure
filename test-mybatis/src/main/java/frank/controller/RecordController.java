package frank.controller;

import frank.model.Record;
import frank.model.Stu;
import frank.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;


    //@GetMapping  只支持get方法
    //@PostMapping  只支持post方法
    @GetMapping("/query")
    public Object query(Integer id) {
        Record record = recordService.query(id);
        return record;
    }

    @PostMapping("/test")
    public Object test(Stu stu) {
        List<Record> records = recordService.test(stu);
        return records;
    }


}
