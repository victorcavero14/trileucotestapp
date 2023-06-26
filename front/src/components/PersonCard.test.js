import { render, screen } from '@testing-library/react';
import React from 'react';
import PersonCard from './PersonCard'; // adjust the path according to your folder structure

describe('PersonCard', () => {
  const mockPerson = {
    name: 'Luke Skywalker',
    birth_year: '19BBY',
    gender: 'male',
    planet_name: 'Tatooine',
    fastest_vehicle_driven: 'Snowspeeder',
    films: [
      { name: 'A New Hope', release_date: '1977-05-25' },
      { name: 'The Empire Strikes Back', release_date: '1980-05-17' },
    ],
  };

  const renderComponent = (person) => {
    return render(<PersonCard person={person} />);
  };

  it('displays person information correctly', () => {
    renderComponent(mockPerson);
    expect(screen.getByText('Name: Luke Skywalker')).toBeInTheDocument();
    expect(screen.getByText('Birth year: 19BBY')).toBeInTheDocument();
    expect(screen.getByText('Gender: male')).toBeInTheDocument();
    expect(screen.getByText('Planet name: Tatooine')).toBeInTheDocument();
    expect(screen.getByText('Fastest vehicle: Snowspeeder')).toBeInTheDocument();
  });

  it('displays person film information correctly', () => {
    renderComponent(mockPerson);
    expect(screen.getByText('Film name: A New Hope')).toBeInTheDocument();
    expect(screen.getByText('Film release date: 1977-05-25')).toBeInTheDocument();
    expect(screen.getByText('Film name: The Empire Strikes Back')).toBeInTheDocument();
    expect(screen.getByText('Film release date: 1980-05-17')).toBeInTheDocument();
  });
});
