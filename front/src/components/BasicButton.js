import LoadingButton from '@mui/lab/LoadingButton';
import * as React from 'react';

export default function BasicButton({ handleFetch, loading }) {
  return (
    <LoadingButton
      loading={loading}
      variant="contained"
      onClick={handleFetch}>
      🔎
    </LoadingButton>
  );
}
