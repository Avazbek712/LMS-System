package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.RoomDTO;
import uz.imv.lmssystem.dto.response.RoomResponseDTO;

import java.util.List;

public interface RoomService {

    List<RoomDTO> getAll();

    RoomDTO getById(Long id);


    void deleteById(Long id);

    RoomResponseDTO save(RoomDTO roomDTO);


    RoomResponseDTO update(Long id, RoomDTO roomDTO);


}
