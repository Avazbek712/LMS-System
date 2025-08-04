package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.LessonDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Group;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {

    void generateAndSaveLessonsForGroup(Group group, LocalDate generationCutoffDate);

}
