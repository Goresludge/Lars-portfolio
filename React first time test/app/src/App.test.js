import { render, screen } from '@testing-library/react';
import { MemoryRouter } from "react-router-dom";
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom/extend-expect';
import App from './App';

function setup() {
  return render(
    <MemoryRouter>
      <App />
    </MemoryRouter>
  );
}

describe('Test add buttons', () => {
  test('test to add max amount of inputs', async () => {

    setup();

    for(let i = 0; i < 7; i++){
      const addButton = screen.getByRole('button', {name: /Add/i});
      userEvent.click(addButton);
    }


  });

});

describe('Test input', () => {
  test('test input data and match preview', async () => {

    setup();

    const nameInput = screen.getByPlaceholderText('Full name');
    userEvent.type(nameInput, 'Test Testperson');
    const adressInput = screen.getByPlaceholderText('Adress');
    userEvent.type(adressInput, 'Testgatan 123');
    const cityInput = screen.getByPlaceholderText('City');
    userEvent.type(cityInput, 'Västerås');

    const link = screen.getByRole('link', { name: /preview/i });
    userEvent.click(link);

    expect(await screen.findByText(/Test Testperson/i)).toBeInTheDocument();
    expect(await screen.findByText(/Testgatan 123/i)).toBeInTheDocument();
    expect(await screen.findByText(/Västerås/i)).toBeInTheDocument();

  });

});