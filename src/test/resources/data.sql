insert into tb_summary
    (id)
values
    ('20aacc59-4637-41a3-8f5a-060625dd4e6c'),
    ('71db0ba3-d679-4dca-819a-cdc6a5ed6abd'),
    ('064b6a5f-df30-4c01-9f3e-5dd0d806560b'),
    ('2f1763f7-7312-48ff-adf5-8ebacf4fe8e3');

insert into tb_item
    (id, id_summary)
values
    ('1ace24dc-be80-4944-9a82-4d453ab31999', '20aacc59-4637-41a3-8f5a-060625dd4e6c'),
    ('7506a57e-8267-4db9-9191-52035eac5259', '20aacc59-4637-41a3-8f5a-060625dd4e6c'),
    ('cb8c2583-c774-45c8-9037-9618db81dc11', '71db0ba3-d679-4dca-819a-cdc6a5ed6abd'),
    ('cc83a33a-ed57-4678-aae5-6f01b6eace1a', '71db0ba3-d679-4dca-819a-cdc6a5ed6abd'),
    ('70aff9a5-d8fe-49f0-a4cf-8204da64d680', '064b6a5f-df30-4c01-9f3e-5dd0d806560b'),
    ('e9b8999f-7b61-430d-a95b-8bdba020cd02', '064b6a5f-df30-4c01-9f3e-5dd0d806560b'),
    ('51741d0e-8ffa-433d-b8cc-db2954592483', '2f1763f7-7312-48ff-adf5-8ebacf4fe8e3'),
    ('af9e69e9-e06b-440c-9ade-508d03af0785', '2f1763f7-7312-48ff-adf5-8ebacf4fe8e3');

insert into tb_payment
    (id, method, id_summary)
values
    ('e1eb1653-85f5-4981-bc6c-2a44e0e00446', 'PIX', '20aacc59-4637-41a3-8f5a-060625dd4e6c'),
    ('f09e6bae-dba1-40cd-be61-d31f9077eb4b', 'DEBITO', '71db0ba3-d679-4dca-819a-cdc6a5ed6abd'),
    ('c77309ad-829a-452e-9bb2-8545455a31e8', 'CREDITO', '064b6a5f-df30-4c01-9f3e-5dd0d806560b'),
    ('6264e223-961e-4fa9-9cfd-69aef3eed8c7', 'BOLETO', '2f1763f7-7312-48ff-adf5-8ebacf4fe8e3');