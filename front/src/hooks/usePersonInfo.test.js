import { act, render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';
import { fetchPersonInfo } from '../api';
import usePersonInfo from './usePersonInfo';

jest.mock('../api');

const TestComponent = () => {
  const { data, loading, error, handleFetch } = usePersonInfo();

  return (
    <div>
      <button onClick={handleFetch}>Fetch</button>
      <div>{loading ? 'Loading...' : 'Loaded'}</div>
      <div>{error}</div>
      <div>{data.map((person, index) => <div key={index}>{person.name}</div>)}</div>
    </div>
  );
};

describe('usePersonInfo', () => {
  it('should handle successful fetch', async () => {
    const mockResponse = {
      name: 'Luke Skywalker',
      birth_year: '19BBY',
      gender: 'male',
      planet_name: 'Tatooine',
      fastest_vehicle_driven: 'Snowspeeder',
      films: [
        {
          name: 'A New Hope',
          release_date: '1977-05-25',
        },
        {
          name: 'The Empire Strikes Back',
          release_date: '1980-05-17',
        },
        {
          name: 'Return of the Jedi',
          release_date: '1983-05-25',
        },
        {
          name: 'Revenge of the Sith',
          release_date: '2005-05-19',
        },
      ],
    };
    fetchPersonInfo.mockResolvedValue(mockResponse);

    render(<TestComponent />);
    
    // eslint-disable-next-line testing-library/no-unnecessary-act
    await act(async () => {
      await userEvent.click(screen.getByText('Fetch'));
    });

    expect(screen.getByText('Luke Skywalker')).toBeInTheDocument();
  });

  it('should handle error during fetch', async () => {
    fetchPersonInfo.mockRejectedValue(new Error('API Error'));

    render(<TestComponent />);

    // eslint-disable-next-line testing-library/no-unnecessary-act
    await act(async () => {
      await userEvent.click(screen.getByText('Fetch'));
    });

    expect(screen.getByText('404 NOT FOUND')).toBeInTheDocument();
  });
});
