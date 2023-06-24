import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import * as React from 'react';

export default function BasicTextFields({ text, setText, error }) {
  return (
    <Box
      component="form"
      sx={{
        '& > :not(style)': { m: 1, width: '25ch' },
      }}
      noValidate
      autoComplete="off"
    >
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
    </Box>
  );
}
