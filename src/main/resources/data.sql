INSERT INTO restaurante (Id, cep, complemento, nome) VALUES
(1l, '0000001', 'Complemento Endereço Restaurante 1', 'Restaurante 1'),
(2l, '0000002', 'Complemento Endereço Restaurante 2', 'Restaurante 2');

INSERT INTO cliente (Id, cep, complemento, nome)  VALUES
(1l, '0000001', 'Complemento Endereço Cliente 1', 'Cliente 1');

INSERT INTO produto (Id, disponivel, nome, valor_unitario, restaurante_id) VALUES
(1l, true, 'Produto 1', 5.0, 1l),
(2l, true, 'Produto 2', 6.0, 1l),
(3l, true, 'Produto 3', 7.0, 2l);

INSERT INTO sacola (Id, forma_pagamento, fechada, valor_total, cliente_id ) VALUES
(1l, 0, false, 0.0, 1L);