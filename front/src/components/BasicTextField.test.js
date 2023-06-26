import { fireEvent, render, screen } from '@testing-library/react';
import React from 'react';
import BasicTextField from './BasicTextField'; // adjust the path according to your folder structure

describe('BasicTextField', () => {
  const setTextMock = jest.fn();

  const renderComponent = ({ text = "", error = null }) => {
    return render(<BasicTextField text={text} setText={setTextMock} error={error} />);
  };

  it('renders without crashing', () => {
    renderComponent({});
    const textField = screen.getByRole('textbox');
    expect(textField).toBeInTheDocument();
  });

  it('sets value and error props correctly', () => {
    renderComponent({ text: "Luke Skywalker", error: "Character not found" });

    const textField = screen.getByRole('textbox');
    expect(textField).toHaveValue('Luke Skywalker');
    expect(textField).toHaveAttribute('aria-invalid', 'true');

    const helperText = screen.getByText('Character not found');
    expect(helperText).toBeInTheDocument();
  });

  it('calls setText when input changes', () => {
    renderComponent({});
    const textField = screen.getByRole('textbox');

    fireEvent.change(textField, { target: { value: 'Darth Vader' } });

    expect(setTextMock).toHaveBeenCalledTimes(1);
    expect(setTextMock).toHaveBeenCalledWith('Darth Vader');
  });
});
