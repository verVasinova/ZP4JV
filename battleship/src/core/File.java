package core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


import boats.Ship;

public class File {

	@SuppressWarnings("static-access")
	public static void saveGame(OutputStream stream, Human p1, AI p2) throws IOException {
		try (DataOutputStream out = new DataOutputStream(stream)) {
			out.writeUTF(p1.getName());
			out.writeInt(p1.getCountOfShots());
			
			for (int j = 0; j < p1.NUMBER_OF_BOATS; j++) {
				out.writeInt(p1.getShip(j).getRow());
				out.writeInt(p1.getShip(j).getColumn());
				out.writeInt(p1.getShip(j).getType().getSize());
				out.writeBoolean(p1.getShip(j).isHorizontal());
			}

			for (int i = 0; i < p1.getBoard().BOARD_SIZE; i++) {
				for (int j = 0; j < p1.getBoard().BOARD_SIZE; j++) {
					Field f = p1.getBoard().getField(i, j);
					out.writeBoolean(f.isShot());
				}
			}

			out.writeUTF(p2.getName());

			for (int j = 0; j < p2.NUMBER_OF_BOATS; j++) {
				out.writeInt(p2.getShip(j).getRow());
				out.writeInt(p2.getShip(j).getColumn());
				out.writeInt(p2.getShip(j).getType().getSize());
				out.writeBoolean(p2.getShip(j).isHorizontal());

			}

			for (int i = 0; i < p2.getBoard().BOARD_SIZE; i++) {
				for (int j = 0; j < p2.getBoard().BOARD_SIZE; j++) {
					Field f2 = p2.getBoard().getField(i, j);
					out.writeBoolean(f2.isShot());
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public static List<Player> loadGame(InputStream stream) throws IOException,
			ClassNotFoundException {
		List<Player> players = new ArrayList<>();

		try (DataInputStream in = new DataInputStream(stream)) {
			Human p1 = null;
			AI p2 = null;

			p1 = new Human();
			p2 = new AI();

			int x, y, s;

			p1.setName(in.readUTF());
			int count  = in.readInt();
			p1.setCountOfShots(count);

			for (int j = 0; j < p1.NUMBER_OF_BOATS; j++) {
				x = in.readInt();
				y = in.readInt();
				s = in.readInt();

				p1.getBoard().setShipOnBoard(new Ship(Ship.getShipType(s)), x,
						y, in.readBoolean());

			}

			boolean shot;
			for (int i = 0; i < p1.getBoard().BOARD_SIZE; i++) {
				for (int j = 0; j < p1.getBoard().BOARD_SIZE; j++) {
					shot = in.readBoolean();
					if (shot) {
						p1.getBoard().getField(i, j).hitField();
					}
				}
			}

			p2.setName(in.readUTF());

			for (int j = 0; j < p2.NUMBER_OF_BOATS; j++) {
				x = in.readInt();
				y = in.readInt();
				s = in.readInt();

				p2.getBoard().setShipOnBoard(new Ship(Ship.getShipType(s)), x,
						y, in.readBoolean());

			}

			for (int i = 0; i < p2.getBoard().BOARD_SIZE; i++) {
				for (int j = 0; j < p2.getBoard().BOARD_SIZE; j++) {
					shot = in.readBoolean();
					if (shot) {
						p2.getBoard().getField(i, j).hitField();
					}
				}
			}

			players.add(p1);
			players.add(p2);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return players;
	}

	public static void save(String name, Human p1, AI p2)
			throws IOException {

		saveGame(new FileOutputStream(name), p1, p2);

	}

	public static List<Player> load(String name) throws IOException, Exception {

		return loadGame(new FileInputStream(name));
	}

}
