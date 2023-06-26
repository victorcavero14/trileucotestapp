import { Card, CardContent, Grid, Typography } from '@mui/material';

const PersonCard = ({person}) => {
  return (
    <Grid item xs={12} sm={6} md={4}>
      <Card variant="outlined">
        <CardContent>
          <Typography color="textSecondary" gutterBottom>
            Person Info
          </Typography>
          <Typography variant="h5" component="div">
            Name: {person.name}
          </Typography>
          <Typography color="textSecondary">
            Birth year: {person.birth_year}
          </Typography>
          <Typography variant="body2" component="p">
            Gender: {person.gender}
          </Typography>
          <Typography variant="body2" component="p">
            Planet name: {person.planet_name}
          </Typography>
          <Typography variant="body2" component="p">
            Fastest vehicle: {person.fastest_vehicle_driven}
          </Typography>
          {
            person?.films && person.films.map((film, index) => 
            <Card key={index} variant="outlined">
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
  );
}

export default PersonCard;
