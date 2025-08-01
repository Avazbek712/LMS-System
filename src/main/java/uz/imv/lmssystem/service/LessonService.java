package uz.imv.lmssystem.service;

import uz.imv.lmssystem.entity.Group;

import java.time.LocalDate;

public interface LessonService {

    void generateAndSaveLessonsForGroup(Group group, LocalDate generationCutoffDate);
}
