import { fireEvent, render, screen } from '@testing-library/react';
import React from 'react';
import BasicButton from './BasicButton';

describe('BasicButton', () => {
  const handleFetchMock = jest.fn();

  const renderComponent = ({ loading = false }) => {
    return render(<BasicButton handleFetch={handleFetchMock} loading={loading} />);
  };

  it('renders without crashing', () => {
    renderComponent({});
    const loadingButton = screen.getByRole('button');
    expect(loadingButton).toBeInTheDocument();
  });

  it('sets loading prop correctly', () => {
    renderComponent({ loading: true });

    const loadingButton = screen.getByRole('button');
    expect(loadingButton).toHaveClass('MuiLoadingButton-loading');
});

  it('calls handleFetch when clicked', () => {
    renderComponent({});
    const loadingButton = screen.getByRole('button');

    fireEvent.click(loadingButton);

    expect(handleFetchMock).toHaveBeenCalledTimes(1);
  });
});
