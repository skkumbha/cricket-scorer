from sqlalchemy import Column, Integer, ForeignKey
from sqlalchemy.orm import relationship
from .base import Base


class Over(Base):
    __tablename__ = 'overs'

    id = Column(Integer, primary_key=True, autoincrement=True)
    innings_id = Column(Integer, ForeignKey('innings.id', ondelete='CASCADE'))
    over_number = Column(Integer)
    bowler_id = Column(Integer, ForeignKey('players.id'))

    # Relationships
    innings = relationship('Innings', back_populates='overs')
    bowler = relationship('Player', back_populates='overs')
    balls = relationship('Ball', back_populates='over', cascade='all, delete-orphan')
