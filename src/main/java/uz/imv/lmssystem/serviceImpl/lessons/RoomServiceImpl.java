package uz.imv.lmssystem.serviceImpl.lessons;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.RoomDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.dto.response.RoomResponseDTO;
import uz.imv.lmssystem.entity.Room;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.exceptions.EntityAlreadyExistsException;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.mapper.RoomMapper;
import uz.imv.lmssystem.repository.RoomRepository;
import uz.imv.lmssystem.service.RoomService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {


    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;


    @Override
    @Transactional
    @Cacheable(value = "rooms_list", key = "'page:' + #page + ':size:' + #size")

    public PageableDTO getAll(Integer page, Integer size) {
        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Room> roomPage = roomRepository.findAll(pageable);
        List<Room> rooms = roomPage.getContent();
        List<RoomDTO> roomDTOS = roomMapper.toDTO(rooms);
        return new PageableDTO(
                roomPage.getSize(),
                roomPage.getTotalElements(),
                roomPage.getTotalPages(),
                !roomPage.isLast(),
                !roomPage.isFirst(),
                roomDTOS
        );
    }

    @Override
    public RoomDTO getById(Long id) {

        Room room = roomRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with id : " + id + " not found!"));

        return roomMapper.toDTO(room);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "rooms", key = "#id"),
            @CacheEvict(value = "rooms_list", allEntries = true)
    })
    public void deleteById(Long id) {

        roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Room with id : " + id + " not found!"));

        roomRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "rooms_list", allEntries = true)
    public RoomResponseDTO save(RoomDTO roomDTO) {
        roomRepository.findByName(roomDTO.getName()).ifPresent(r -> {
            throw new EntityAlreadyExistsException("Room with name : " + roomDTO.getName() + " already exist!");
        });

        roomRepository.findByRoomNumber(roomDTO.getRoomNumber()).ifPresent(r -> {
            throw new EntityAlreadyExistsException("Room with number : " + roomDTO.getRoomNumber() + " already exist!");
        });

        if (roomDTO.getChairs() < roomDTO.getCapacity() && roomDTO.getDesks() < roomDTO.getCapacity() / 2) {
            throw new IllegalArgumentException("The number of chairs and desks cannot be less than the capacity!");
        }

        Room room = roomMapper.toEntity(roomDTO);
        roomRepository.save(room);

        return roomMapper.toResponse(room);
    }

    @Override
    @Transactional
    public RoomResponseDTO update(Long id, RoomDTO roomDTO) {

        roomRepository.findByName(roomDTO.getName()).ifPresent(r -> {
            if (!r.getId().equals(id))
                throw new EntityAlreadyExistsException("Room with name : " + roomDTO.getName() + " already exist!");
        });

        roomRepository.findByRoomNumber(roomDTO.getRoomNumber()).ifPresent(r -> {
            if (!r.getId().equals(id))
                throw new EntityAlreadyExistsException("Room with number : " + roomDTO.getRoomNumber() + " already exist!");
        });

        Room room = roomRepository.
                findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with id : " + id + " not found!"));

        if (roomDTO.getChairs() < roomDTO.getCapacity() && roomDTO.getDesks() < roomDTO.getCapacity() / 2) {
            throw new IllegalArgumentException("The number of chairs and desks cannot be less than the capacity!");
        } else {

            roomMapper.updateEntity(roomDTO, room);
            roomRepository.save(room);
            return roomMapper.toResponse(room);
        }
    }
}
