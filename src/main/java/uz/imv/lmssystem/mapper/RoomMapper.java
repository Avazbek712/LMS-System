package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.imv.lmssystem.dto.RoomDTO;
import uz.imv.lmssystem.dto.response.RoomResponseDTO;
import uz.imv.lmssystem.entity.Room;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {
    RoomResponseDTO toResponse(Room room);

    RoomDTO toDto(Room room);
}