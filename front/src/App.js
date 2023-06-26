import { Grid } from '@mui/material';
import './App.css';
import BasicButton from './components/BasicButton';
import BasicTextField from './components/BasicTextField';
import PersonCard from './components/PersonCard';
import usePersonInfo from './hooks/usePersonInfo';

function App() {
  const { textInput, setTextInput, data, loading, error, handleFetch } = usePersonInfo();

  return (
    <div className="App">
      <div className="Flex">
        <BasicTextField text={textInput} setText={setTextInput} error={error} />
        <BasicButton handleFetch={handleFetch} loading={loading} />
      </div>
      <Grid container spacing={3} p={4}>
        {data.length > 0 ? (
          data.map((pers, index) => <PersonCard key={index} person={pers} />)
        ) : (
          'Start searching your favourite Star Wars Character 🪐'
        )}
      </Grid>
    </div>
  );
}

export default App;
