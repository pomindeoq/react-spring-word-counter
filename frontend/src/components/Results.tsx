import React from "react";
import { Container } from "react-bootstrap";
import Table from "react-bootstrap/Table";

const Results = (props) => {
  return (
    <Container>
      {props.data.map((group) => {
        return (<div key={group.name}>
            <h4>{group.name}</h4>
            <Table striped bordered hover size="sm">
              <thead>
                <tr>                
                  <th>Word</th>
                  <th>Count</th>
                </tr>
              </thead>
              <tbody>
                  {group.words.map((result) => {
                    return (
                      <tr key={result.word}>
                        <td>{result.word}</td>
                        <td>{result.count}</td>
                      </tr>
                    );
                  })}              
              </tbody>
            </Table>
          </div>
      )})}
    </Container>
  );
};

export default Results;
