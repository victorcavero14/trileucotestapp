import { useState } from 'react';
import { fetchPersonInfo } from '../api';

const usePersonInfo = () => {
  const [textInput, setTextInput] = useState('');
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleFetch = async () => {
    setLoading(true);
    fetchPersonInfo(textInput)
      .then((personInfo) => {
        setData((val) => [...val, personInfo]);
        setError(null);
      })
      .catch(() => {
        setError('404 NOT FOUND');
      })
      .finally(() => setLoading(false));
  };

  return { textInput, setTextInput, data, loading, error, handleFetch };
};

export default usePersonInfo;
