package com.example.project.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.repository.UUIDRepository;
import com.example.project.response.GroupTypeResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/uuid")
@AllArgsConstructor
public class UUIDController {

    private UUIDRepository uuidRepository;

    @GetMapping("/{uuid}")
    public ResponseEntity<GroupTypeResponse> getGroupTypeByUUID(@PathVariable(name = "uuid") String uuid) {
        String sportType = uuidRepository.getGroupTypeByUUID(uuid);
        if (sportType == null) {
            return new ResponseEntity<GroupTypeResponse>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        String sport = null;
        switch (sportType) {
            case "FootballGroup":
                sport = "FOOTBALL";
                break;

            case "BasketballGroup":
                sport = "BASKETBALL";
                break;

            default:
            sport = "";
                break;
        }

        GroupTypeResponse response = new GroupTypeResponse();
        response.setGroupType(sport);

        return new ResponseEntity<GroupTypeResponse>(response, new HttpHeaders(), HttpStatus.OK);
    }
    
}