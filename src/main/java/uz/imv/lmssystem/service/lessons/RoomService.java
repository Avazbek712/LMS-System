package uz.imv.lmssystem.service.lessons;

import uz.imv.lmssystem.dto.RoomDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.dto.response.RoomResponseDTO;

public interface RoomService {

    PageableDTO getAll(Integer page, Integer size);

    RoomDTO getById(Long id);


    void deleteById(Long id);

    RoomResponseDTO save(RoomDTO roomDTO);


    RoomResponseDTO update(Long id, RoomDTO roomDTO);


}
