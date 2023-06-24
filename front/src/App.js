import { Card, CardContent, Grid, Typography } from '@mui/material';
import { useState } from 'react';
import './App.css';
import { fetchPersonInfo } from './api'; // ensure this is a valid function
import BasicButtons from './components/BasicButtons';
import BasicTextFields from './components/BasicTextFields';


function App() {
  const [textInput, setTextInput] = useState('');
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleFetch = async () => {
    setLoading(true);
    fetchPersonInfo(textInput).then((personInfo) => {
      setData((val) => [...val,personInfo]);
      setLoading(false);
      setError(null);
    }).catch((err) => {
      setError("404 NOT FOUND");
      setLoading(false);
    })
  };

  return (
    <div className="App">
      <div className="Flex">
        <BasicTextFields text={textInput} setText={setTextInput} error={error}/>
        <BasicButtons handleFetch={handleFetch} loading={loading}/>
      </div>
      <Grid container spacing={3} p={4}>
          {data ? (
            data.map((pers) => 
            <Grid item xs={12} sm={6} md={4}>
              <Card variant="outlined">
                <CardContent>
                  <Typography color="textSecondary" gutterBottom>
                    Person Info
                  </Typography>
                  <Typography variant="h5" component="div">
                    Name: {pers.name}
                  </Typography>
                  <Typography color="textSecondary">
                    Birth year: {pers.birth_year}
                  </Typography>
                  <Typography variant="body2" component="p">
                    Gender: {pers.gender}
                  </Typography>
                  <Typography variant="body2" component="p">
                    Planet name: {pers.planet_name}
                  </Typography>
                  <Typography variant="body2" component="p">
                    Fastest vehicle: {pers.fastest_vehicle_driven}
                  </Typography>
                  {
                    pers.films.map((film) => 
                    <Card variant="outlined">
                      <Typography variant="body2" component="p">
                        Film name: {film.name}
                      </Typography>
                      <Typography variant="body2" component="p">
                        Film release date: {film.release_date}
                      </Typography>
                    </Card>
                    )
                  }
                </CardContent>
              </Card>
            </Grid>
            )
        ): "Start searching your favorite Star Wars Character 🪐"}
      </Grid>
    </div>
  );
}

export default App;
