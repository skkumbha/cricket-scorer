package com.cricket.scorer.mapper;

import com.cricket.scorer.dto.PlayerDTO;
import com.cricket.scorer.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlayerMapper {
    
    PlayerDTO toDto(Player player);
    
    Player toEntity(PlayerDTO playerDTO);
    
    List<PlayerDTO> toDtoList(List<Player> players);
    
    void updateEntityFromDto(PlayerDTO playerDTO, @MappingTarget Player player);
}
