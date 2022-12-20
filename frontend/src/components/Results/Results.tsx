import React from "react";
import { Alert, Container, Row } from "react-bootstrap";
import Table from "react-bootstrap/Table";
import { Data, Word } from "../../models/response";

interface ResultsProps {
  data: Data[];
}

const Results: React.FC<ResultsProps> = ({ data }) => {
  if (data.length === 0) {
    return (
      <Container fluid className="col-md-6 mt-md-3">
        <Row align="center">
          <Alert variant="warning">Results are empty</Alert>
        </Row>
      </Container>
    );
  }

  return (
    <Container fluid className="mb-md-5">
      {data.map((group: Data) => {
        return (
          <div key={group.name}>
            <br />
            <h4>{group.name}</h4>
            <Table striped bordered hover size="sm" responsive="md">
              <thead>
                <tr>
                  <th className="col-md-4">Word</th>
                  <th className="col-md-4">Count</th>
                </tr>
              </thead>
              <tbody>
                {group.words.map((result: Word) => {
                  return (
                    <tr key={result.word}>
                      <td className="col-md-4">{result.word}</td>
                      <td className="col-md-4">{result.count}</td>
                    </tr>
                  );
                })}
              </tbody>
            </Table>
          </div>
        );
      })}
    </Container>
  );
};

export default Results;
