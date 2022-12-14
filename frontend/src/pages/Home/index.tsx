import React, { useState } from "react";
import {
  Container,
  Row,
  Col,
  Form,
  Button,
  ProgressBar,
  Alert,
} from "react-bootstrap";
import http from "../../api/http";
import Results from "../../components/Results";

function App() {
  const [selectedFiles, setSelectedFiles] = useState([]);
  const [progress, setProgress] = useState(null);
  const [error, setError] = useState("");
  const [data, setData] = useState([]);

  const uploadHandler = (e: React.FormEvent<HTMLElement>) => {
    e.preventDefault();
    let formData = new FormData();
    console.log(selectedFiles)
    for (let i = 0 ; i < selectedFiles.length ; i++) {
      formData.append("file", selectedFiles[i]);
    }
    console.log(formData.getAll("file"));
    setProgress(null);
    setError("");
    http
      .post("/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress: (data) => {
          //Set the progress value to show the progress bar
          setProgress(Math.round((100 * data.loaded) / data.total));
        },
      })
      .then((response) => {
        setData(response.data);
        setError("");
      })
      .catch((error) => {
        console.log(error);
         if(error?.response) {
          const { code, message } = error?.response?.data;
          switch (code) {
            case "FILE_EMPTY":
            case "FILE_MISSING":
            case "FILE_SIZE_TOO_LARGE":
            case "FILE_TYPE_NOT_VALID":
              setError(message);
              break;
            default:
              setError("Sorry! Something went wrong. Please try again later");
              break;
          }
        } else {     
          setError(error.message);
        }
      });
  };
  return (
    <Container fluid="md">
      <Row className="justify-content-md-center">
        <Col className="col-md-4">
          <Form
            action="http://localhost:8080/upload"
            method="post"
            encType="multipart/form-data"
            onSubmit={uploadHandler}
          >
            <Form.Group
              onChange={(e: React.FormEvent<HTMLElement>) => {
                setSelectedFiles(e.target.files);
              }}
              controlId="formFileMultiple"
              className="mb-3"
            >
              <Form.Label>Word count from files app</Form.Label>
              <Form.Control type="file" multiple />
            </Form.Group>
            <Form.Group className="mb-3">
              <Button variant="info" type="submit">
                Upload
              </Button>
            </Form.Group>
            {error && <Alert variant="danger">{error}</Alert>}
            {!error && progress && (
              <ProgressBar now={progress} label={`${progress}%`} />
            )}
          </Form>
        </Col>
      </Row>
      {data && <Results data={data} />}
    </Container>
  );
}

export default App;
