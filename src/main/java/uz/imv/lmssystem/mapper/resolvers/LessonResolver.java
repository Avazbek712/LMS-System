package uz.imv.lmssystem.mapper.resolvers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.imv.lmssystem.entity.Lesson;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.repository.lessons.LessonRepository;

@Component
@RequiredArgsConstructor
public class LessonResolver {
    private final LessonRepository lessonRepository;

    public Lesson resolve(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson with id " + lessonId + " not found"));
    }
}


