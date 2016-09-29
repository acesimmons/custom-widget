package edu.jsu.mcis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CustomWidget extends JPanel implements MouseListener {
    private java.util.List<ShapeObserver> observers;
    
    
    private final Color HEXAGON_SELECTED_COLOR = Color.green;
	private final Color OCTAGON_SELECTED_COLOR = Color.red;
    private final Color DEFAULT_COLOR = Color.white;
    private boolean hexagonSelected;
	private boolean octagonSelected;
    private Point[] hexagonVertex;
	private Point[] octagonVertex;

    
    public CustomWidget() {
        observers = new ArrayList<>();
        Dimension dim = getPreferredSize();
        hexagonSelected = true;
		octagonSelected = false;
		
        hexagonVertex = new Point[6];
        for(int i = 0; i < hexagonVertex.length; i++) { hexagonVertex[i] = new Point(); }
		calculateHexagonVertices(dim.width, dim.height);
		
		octagonVertex = new Point[8];
        for(int i = 0; i < octagonVertex.length; i++) { octagonVertex[i] = new Point(); }
		calculateOctagonVertices(dim.width, dim.height);
        
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);
    }

    
    public void addShapeObserver(ShapeObserver observer) {
        if(!observers.contains(observer)) observers.add(observer);
    }
    public void removeShapeObserver(ShapeObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers() {
        ShapeEvent event = new ShapeEvent(hexagonSelected, octagonSelected);
        for(ShapeObserver obs : observers) {
            obs.shapeChanged(event);
        }
    }
    
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    private void calculateHexagonVertices(int width, int height) {
        int side = Math.min(width, height) / 4;
        Point[] sign = {new Point(-1, -2), new Point(1, -2), new Point(2, 0), new Point(1, 2), new Point(-1,2), new Point(-2,0)};
        for(int i = 0; i < hexagonVertex.length; i++) {
            hexagonVertex[i].setLocation(width/4 + sign[i].x * side/4, 
                                  height/4 + sign[i].y * side/4);
        }
    }
	
	private void calculateOctagonVertices(int width, int height) {
        int side = Math.min(width, height) / 4;
        Point[] sign = {new Point(5, -2), new Point(3, -2), new Point(2, -1), new Point(2, 1), new Point(3,2), new Point(5,2), 
		new Point(6, 1), new Point(6, -1)};
        for(int i = 0; i < octagonVertex.length; i++) {
            octagonVertex[i].setLocation(width/4 + sign[i].x * side/4, 
                                  height/4 + sign[i].y * side/4);
        }
    }
    
       @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        calculateHexagonVertices(getWidth(), getHeight());
		calculateOctagonVertices(getWidth(), getHeight());
        Shape hexagon = getHexagon();
		Shape octagon = getOctagan();
        g2d.setColor(Color.black);
        g2d.draw(hexagon);
        if(hexagonSelected) {
            g2d.setColor(HEXAGON_SELECTED_COLOR);
            g2d.fill(hexagon);
        }
        else {
            g2d.setColor(DEFAULT_COLOR);
            g2d.fill(hexagon);            
        }
		g2d.setColor(Color.black);
		g2d.draw(octagon);
		if(octagonSelected){
			g2d.setColor(DEFAULT_COLOR);
			g2d.fill(octagon);
		}
		else{
			g2d.setColor(OCTAGON_SELECTED_COLOR);
			g2d.fill(octagon);
		}
    }

    public void mouseClicked(MouseEvent event) {
        Shape shape = getHexagon();
        if(shape.contains(event.getX(), event.getY())) {
            hexagonSelected = !hexagonSelected;
			octagonSelected = !hexagonSelected;
            notifyObservers();
        }
		else{
			shape = getOctagan();
			if(shape.contains(event.getX(), event.getY())){
				hexagonSelected = !octagonSelected;
				octagonSelected = !octagonSelected;
				notifyObservers();
			}
		}
        repaint(shape.getBounds());
    }
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    
    public Shape getHexagon() {
        int[] x = new int[hexagonVertex.length];
        int[] y = new int[hexagonVertex.length];
        for(int i = 0; i < hexagonVertex.length; i++) {
            x[i] = hexagonVertex[i].x;
            y[i] = hexagonVertex[i].y;
        }
        Shape shape = new Polygon(x, y, hexagonVertex.length);
        return shape;
    }
	
	 public Shape getOctagan() {
        int[] x = new int[octagonVertex.length];
        int[] y = new int[octagonVertex.length];
        for(int i = 0; i < octagonVertex.length; i++) {
            x[i] = octagonVertex[i].x;
            y[i] = octagonVertex[i].y;
        }
        Shape shape = new Polygon(x, y, octagonVertex.length);
        return shape;
    }
	
    public boolean isSelected() { 
	return hexagonSelected || octagonSelected; 
	}

	public static void main(String[] args) {
		JFrame window = new JFrame("Custom Widget");
        window.add(new CustomWidget());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300, 300);
        window.setVisible(true);
	}
}