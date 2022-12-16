import React, { FormEvent, FormEventHandler } from "react";
import { Alert, Button, Form, ProgressBar } from "react-bootstrap";

interface UploadFormProps {
    onSelectedFiles: Function,
    onUpload: FormEventHandler,
    error: string,
    progress: number | null,
  }

const UploadForm = ({error, progress, onSelectedFiles, onUpload}: UploadFormProps) => {
  return (
    <Form
      action="http://localhost:8080/upload"
      method="post"
      encType="multipart/form-data"
      onSubmit={onUpload}
    >
      <Form.Group
        onChange={(event: FormEvent) => {
          onSelectedFiles((event.target as HTMLInputElement).files);
        }}
        controlId="formFileMultiple"
        className="mb-3"
      >
        <br />
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
  );
}
export default UploadForm;

