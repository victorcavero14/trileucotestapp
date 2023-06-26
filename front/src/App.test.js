import { fireEvent, render, screen } from '@testing-library/react';
import App from './App';
import usePersonInfo from './hooks/usePersonInfo';

jest.mock('./hooks/usePersonInfo');

describe('App', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  it('renders correctly', () => {
    usePersonInfo.mockReturnValue({
      textInput: '',
      setTextInput: jest.fn(),
      data: [],
      loading: false,
      error: null,
      handleFetch: jest.fn(),
    });

    render(<App />);

    expect(screen.getByText('Start searching your favourite Star Wars Character 🪐')).toBeInTheDocument();
    expect(screen.getByRole('button')).toBeInTheDocument();
    expect(screen.getByRole('textbox')).toBeInTheDocument();
  });

  it('renders PersonCard when there is data', () => {
    usePersonInfo.mockReturnValue({
      textInput: 'Luke',
      setTextInput: jest.fn(),
      data: [{ name: 'Luke', birth_year: '19BBY', gender: 'male' }],
      loading: false,
      error: null,
      handleFetch: jest.fn(),
    });

    render(<App />);

    expect(screen.getByText('Name: Luke')).toBeInTheDocument();
    expect(screen.getByText('Birth year: 19BBY')).toBeInTheDocument();
    expect(screen.getByText('Gender: male')).toBeInTheDocument();
  });

  it('calls handleFetch when button is clicked', () => {
    const handleFetch = jest.fn();

    usePersonInfo.mockReturnValue({
      textInput: '',
      setTextInput: jest.fn(),
      data: [],
      loading: false,
      error: null,
      handleFetch,
    });

    render(<App />);
    
    fireEvent.click(screen.getByRole('button'));

    expect(handleFetch).toHaveBeenCalled();
  });
});
