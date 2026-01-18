from sqlalchemy import Column, Integer, ForeignKey
from sqlalchemy.orm import relationship
from .base import Base


class TeamPlayer(Base):
    __tablename__ = 'team_players'

    team_id = Column(Integer, ForeignKey('teams.id', ondelete='CASCADE'), primary_key=True)
    player_id = Column(Integer, ForeignKey('players.id', ondelete='CASCADE'), primary_key=True)

    # Relationships
    team = relationship('Team', back_populates='team_players')
    player = relationship('Player', back_populates='team_players')
