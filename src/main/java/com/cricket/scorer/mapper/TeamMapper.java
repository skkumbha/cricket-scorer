package com.cricket.scorer.mapper;

import com.cricket.scorer.dto.TeamDTO;
import com.cricket.scorer.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeamMapper {
    
    TeamDTO toDto(Team team);
    
    Team toEntity(TeamDTO teamDTO);
    
    List<TeamDTO> toDtoList(List<Team> teams);
    
    void updateEntityFromDto(TeamDTO teamDTO, @MappingTarget Team team);
}
