package uz.imv.lmssystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.RoomDTO;
import uz.imv.lmssystem.dto.response.RoomResponseDTO;
import uz.imv.lmssystem.entity.Room;
import uz.imv.lmssystem.exceptions.EntityAlreadyExistsException;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.mapper.RoomMapper;
import uz.imv.lmssystem.repository.RoomRepository;

import java.util.List;

/**
 * Created by Avazbek on 24/07/25 11:53
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {


    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<RoomDTO> getAll() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream().map(roomMapper::toDto).toList();

    }

    @Override
    public RoomDTO getById(Long id) {

        Room room = roomRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with id : " + id + " not found!"));

        return roomMapper.toDto(room);
    }

    @Override
    public void deleteById(Long id) {

        roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Room with id : " + id + " not found!"));

        roomRepository.deleteById(id);
    }

    @Override
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

        Room room = new Room();

        room.setName(roomDTO.getName().toUpperCase());
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setCapacity(roomDTO.getCapacity());
        room.setDesks(roomDTO.getDesks());
        room.setChairs(roomDTO.getChairs());

        roomRepository.save(room);

        return roomMapper.toResponse(room);
    }

    @Override
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

        room.setName(roomDTO.getName().toUpperCase());
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setCapacity(roomDTO.getCapacity());
        room.setDesks(roomDTO.getDesks());
        room.setChairs(roomDTO.getChairs());

        roomRepository.save(room);

        return roomMapper.toResponse(room);
    }
}
