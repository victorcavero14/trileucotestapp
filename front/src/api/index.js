export const fetchPersonInfo = async (textInput) => {
    return fetch("http://localhost:8080/swapi-proxy/person-info?name=" + textInput).then(res => res.json())
}