import TextField from '@mui/material/TextField';
import * as React from 'react';

export default function BasicTextField({ text, setText, error }) {
  return (
      <TextField
        error={error ? true : false}
        helperText={error}
        id="outlined-basic"
        label="Search a star wars character"
        variant="outlined"
        size="small"
        value={text}
        onChange={(e) => setText(e.target.value)}
      />
  );
}
