package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uz.imv.lmssystem.dto.RoomDTO;
import uz.imv.lmssystem.dto.RoomDTO;
import uz.imv.lmssystem.dto.response.RoomResponseDTO;
import uz.imv.lmssystem.entity.Room;
import uz.imv.lmssystem.entity.Room;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {
    RoomResponseDTO toResponse(Room room);

    RoomDTO toDTO(Room room);

    List<RoomDTO> toDTO(List<Room> rooms);

    Room toEntity(RoomDTO dto);

    void updateEntity(RoomDTO dto, @MappingTarget Room entity);
}