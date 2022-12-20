import { AxiosProgressEvent, AxiosResponse } from "axios";
import React, { useState } from "react";
import { Container, Row, Col, Spinner } from "react-bootstrap";
import api from "../../api/api";
import Results from "../../components/Results/Results";
import UploadForm from "../../components/UploadForm/UploadForm";
import { Data } from "../../models/response";
import "./styles.css";

function App() {
  const [selectedFiles, setSelectedFiles] = useState<FileList | null>(null);
  const [progress, setProgress] = useState<any>(null);
  const [error, setError] = useState<string>("");
  const [data, setData] = useState<Data[]>([]);
  const [loading, setLoading] = useState<boolean>(false);

  const onSelectedFiles = (files: FileList) => {
    setSelectedFiles(files);
  };

  const uploadHandler = (e: React.FormEvent): void => {
    e.preventDefault();
    setLoading(true);
    let formData = new FormData();
    if (selectedFiles != null && selectedFiles.length) {
      for (let i = 0; i < selectedFiles!.length; i++) {
        formData.append("file", selectedFiles?.item(i) as File);
      }
    }
    setProgress(null);
    setError("");
    api
      .post("/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress: (data: AxiosProgressEvent) => {
          let total = data?.total ? data.total : 0;
          setProgress(Math.round((100 * data?.loaded) / total));
        },
      })
      .then((response: AxiosResponse) => {
        setData(response.data);
        setError("");
        setLoading(false);
      })
      .catch((error) => {
        if (error?.response) {
          const { code, message } = error?.response?.data;
          switch (code) {
            case "FILES_EMPTY":
            case "FILES_MISSING":
            case "FILES_SIZE_TOO_LARGE":
            case "FILES_TYPE_NOT_VALID":
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
    setLoading(false);
  };
  return (
    <Container fluid="md">
      <Row className="justify-content-md-center">
        <Col className="col-md-4" align="center">
          <br />
          <h5>Word Counter from files application</h5>
          <UploadForm
            onUpload={uploadHandler}
            onSelectedFiles={onSelectedFiles}
            error={error}
            progress={progress}
          />
        </Col>
      </Row>
      <Row className="justify-content-md-center">
        <Col className="col-md-8">
          {!loading && <Results data={data} />}
          {loading && (
            <Spinner className="spinner" animation="grow" variant="info" />
          )}
        </Col>
      </Row>
    </Container>
  );
}

export default App;
