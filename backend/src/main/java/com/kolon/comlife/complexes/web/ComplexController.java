package com.kolon.comlife.complexes.web;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Example Controller
 */
@RestController
@RequestMapping("/complexes")
public class ComplexController {
    private static final Logger logger = LoggerFactory.getLogger(ComplexController.class);

    @Resource(name = "complexService")
    private ComplexService complexService;

    @GetMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getComplexAll() {
        List<ComplexInfo> complexInfoList = complexService.getComplexList();
        return ResponseEntity.status(HttpStatus.OK).body(complexInfoList);
    }

    @GetMapping(
            path = "/kvm/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getComplexKvmByCmplxId() {
        List<ComplexInfo>         complexInfoList = complexService.getComplexList();
        Map<Integer, ComplexInfo> kvm = new HashMap();
        ComplexInfo               cmplxInfo;
        Iterator<ComplexInfo>     iter = complexInfoList.iterator();

        while( iter.hasNext() ) {
            cmplxInfo = iter.next();
            kvm.put( Integer.valueOf(cmplxInfo.getCmplxId()), cmplxInfo );
        }

        return ResponseEntity.status(HttpStatus.OK).body( kvm );
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComplexInfo>  getComplexById(@PathVariable("id") int id) {
        ComplexInfo complexInfo = complexService.getComplexById(id);
        if (complexInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(complexInfo);
    }

    // NOT SUPPORTED [POST]
    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComplexInfo> setExampleInJson(@RequestBody ComplexInfo complex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    // NOT SUPPORTED [PUT]
    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComplexInfo> updateExampleInJson(@PathVariable("id") int id,
                                                           @RequestBody ComplexInfo complex) {

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    // NOT SUPPORTED METHOD [PUT]
    @DeleteMapping(
            value = "/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComplexInfo> deleteExampleInJson(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

}