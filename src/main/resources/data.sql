INSERT INTO PARKING_LOT (address, name) VALUES
    ('Via dei Test', 'TestParkingLot')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO PARKING_LOT (address, name) VALUES
    ('Via dei Test', 'TestParkingLot2')
    ON CONFLICT (name) DO NOTHING;
