import React from "react";
import { Container } from "react-bootstrap";
import Table from "react-bootstrap/Table";
import { Data, Word } from "../../models/response";

interface ResultsProps {
  data: Data[];
}

const Results: React.FC<ResultsProps> = ({ data }) => {
  return (
    <Container>
        {data.map((group: Data) => {
          return (
            <div key={group.name}>
              <div className="row">
                <div className="col-md-8 offset-md-2">
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
              </div>
            </div>
          );
        })}
    </Container>
  );
};

export default Results;
