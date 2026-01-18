from sqlalchemy import Column, Integer, ForeignKey, DECIMAL
from sqlalchemy.dialects.postgresql import UUID
from sqlalchemy.orm import relationship
from .base import Base


class Innings(Base):
    __tablename__ = 'innings'

    id = Column(Integer, primary_key=True, autoincrement=True)
    match_id = Column(UUID(as_uuid=True), ForeignKey('matches.id', ondelete='CASCADE'))
    batting_team_id = Column(Integer, ForeignKey('teams.id'))
    bowling_team_id = Column(Integer, ForeignKey('teams.id'))
    innings_number = Column(Integer)
    total_runs = Column(Integer, default=0)
    wickets = Column(Integer, default=0)
    overs_completed = Column(DECIMAL(4, 1), default=0)

    # Relationships
    match = relationship('Match', back_populates='innings')
    batting_team = relationship('Team', foreign_keys=[batting_team_id], back_populates='batting_innings')
    bowling_team = relationship('Team', foreign_keys=[bowling_team_id], back_populates='bowling_innings')
    overs = relationship('Over', back_populates='innings', cascade='all, delete-orphan')
