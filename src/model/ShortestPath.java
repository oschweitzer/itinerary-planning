package model;
/********************************************************************/
/* PlusCourtChemin.java                                             */
/* Modification de Dijkstra.java pour fonctionnement                */
/* avec le projet de calculs d'itin�raires                          */
/********************************************************************/

import java.awt.*;
import java.util.*;

class Node {
	/** The x coordinate */
	int	x;
	
	/** The y coordinate */
	int	y;
	
	/** Start node */
	int	delta_plus;	/* edge starts from this node */
	
	/** End node */
	int	delta_minus;	/* edge terminates at this node */
	
	/** Distance from the start node */
	int	dist;
	
	/** Previous node of the shortest path */
	int	prev;
	
	/** Node in Sbar with finite distance. */
	int	succ;
	
	/** Node in Sbar with finite distance. */
	int pred;	
	
	/** Weigth */
	int	w;
	
	
	int	h;
	int	pw;
	int	dx;
	int	dy;
	String	name;
    int id;
}

class Edge {
	
	/** Initial vertex of this edge */
	int	rndd_plus;
	
	/** Terminal vertex of this edge */
	int	rndd_minus;
	
	/** Edge starts from rndd_plus */
	int	delta_plus;
	
	/** Edge terminates at rndd_minus */
	int	delta_minus;
	
	/** Length */
	int	len;
	
	/** Name */
	String	name;
	
	/** Id */
    int id;
}

/**
 * The Class ShortestPath.
 */
public class ShortestPath {
	
	/** The zoom. */
	float zoom;
	
	/** The m. */
	int	n,m;
	
	
	int	u;
	
	/** The start node. */
	int snode;
        
    /** The enode. */
    int     enode; // end node
	
	/** The pre_s_last. */
	int	pre_s_first, pre_s_last;
	
	/** The isdigraph. */
	boolean	isdigraph;
	
	/** The iteration. */
	int	iteration;
	
	/** The step. */
	int step;
	
	/** The v. */
	Node	v[];
	
	/** The e. */
	Edge	e[];

    /** The road network. */
    RoadNetwork reseau;
    
    /** The p1. */
    int p1;
    
    /** The p2. */
    int p2;
    
    /** The path. */
    Vector<ItineraryState> chemin = new Vector<ItineraryState>();
    
    /** Is the goal find. */
    boolean findGoal=false;

	/**
	 * Find node.
	 *
	 * @param name the name
	 * @return the int
	 */
	int findNode(String name) {
		for (int i=0; i<n; i++)
			if (v[i].name.equals(name))
				return i;
		return -1;
	}

    /**
     * Find node.
     *
     * @param id the id
     * @return the int
     */
    int findNode(int id) {
            for (int i=0; i<n; i++)
                    if (v[i].id==id)
                            return i;
            return -1;
    }
    
    // Fonction renvoyant le nom de la route d'un arc
    /**
     * Find edge.
     *
     * @param numNode1 the node number 1
     * @param numNode2 the node number 2
     * @return the edge number
     */
    public int findEdge(int numNode1, int numNode2) {
    	int r = -1;
    	int numPt1, numPt2;
    	for (int i = 0; i < m; i++) {
    		numPt1 = v[e[i].rndd_minus].id;
    		numPt2 = v[e[i].rndd_plus].id;
    		if (((numNode1 == numPt1) && (numNode2 == numPt2)) || ((numNode1 == numPt2) && (numNode2 == numPt1))) {
    			r = i;
    			break;
    		}
    	}
    	return r;
    }
    
    /**
     * Gets the edge name.
     *
     * @param id the id
     * @return the edge name
     */
    public String getEdgeName(int id) {
    	return e[id].name;
    }
    
    /**
     * Gets the edge length.
     *
     * @param id the id
     * @return the edge length
     */
    public int getEdgeLength(int id) {
    	return e[id].len;
    }
    
    /**
     * Gets the node coordinates
     *
     * @param id the id
     * @return the node coordinates
     */
    public Point getNodeCoords(int id) {
    	Node node = v[findNode(id)];
    	return new Point(node.x, node.y);
    }
    
    /**
     * Gets the node id.
     *
     * @param id the id
     * @return the node id
     */
    public int getNodeId(int id) {
    	return v[id].id;
    }
    
    /**
     * Cherche point proche.
     *
     * @param pt the point
     * @return the closest point
     */
    public int cherchePointProche(Point pt) {
		// Recherche le point le plus proche de la souris
		Point pointCourant;
		Node node;
		int i, idPointProche = -1;
		double meilleureDistance = 1000, distanceCourante;
        for (i = 0; i < n; i++) {
        	node = v[i];
        	pointCourant = new Point(node.x, node.y);
        	distanceCourante = pointCourant.distance(pt);
        	if( distanceCourante < meilleureDistance){
        		meilleureDistance = distanceCourante;
        		idPointProche = node.id;
        	}
		}
        return idPointProche;
    }

	/**
	 * Input_graph.
	 *
	 * @param res the road network
	 */
	void input_graph(RoadNetwork res) {

        n = res.getNombrePoints();
        m = res.getNombreConnexions();
        isdigraph = false;
        double z = (double)zoom;
        
        // Mise en place des points
        int i = 0;
        Integer numPoint;
        Point pt;
        
        for (@SuppressWarnings("rawtypes")
		Iterator f = res.getListePoints().iterator(); f.hasNext();) {
          	numPoint = (Integer) f.next();
         	pt = res.getPoint(numPoint);
	        Node node = new Node();
	        node.name = new String(numPoint.toString());
	        node.id = numPoint;
	        node.x = (int) (pt.getX()*z);
	        node.y = (int) (pt.getY()*z);
	        v[i++] = node;
        }
        
        // Mise en place des connexions entre les points gr�ce aux routes
        i = 0;
        String nomRoute;
        Road route = null;
        int ipt;
        Integer ptCourant, ptPrecedent;
        Edge edge;
        
        for (@SuppressWarnings("rawtypes")
		Iterator f = res.getListeRoutes().iterator(); f.hasNext();) {
         	nomRoute = (String) f.next();
            route = res.getRoute(nomRoute);
            ptPrecedent = null;
            for (ipt = 0; ipt < route.getNombrePoints(); ipt++)
            {
            	ptCourant = route.getNumPoint(ipt);
            	if (ptPrecedent != null)
            	{
			        edge = new Edge();
			        edge.name = new String(nomRoute);
			        edge.id = i;
			        //edge.rndd_plus = findNode(new String(""+C.getEnd().getId()));
			        //edge.rndd_minus = findNode(new String(""+C.getStart().getId()));
			        edge.rndd_plus = findNode(ptCourant);
			        edge.rndd_minus = findNode(ptPrecedent);
			        edge.len = (int) res.getPoint(ptCourant).distance(res.getPoint(ptPrecedent));
			        e[i++] = edge;
            	}
            	ptPrecedent = ptCourant;
            }
        }

		for (i=0; i<n; i++) {
			v[i].succ = v[i].pred = -2;
			v[i].prev = v[i].dist = -1;
			v[i].pw = 0;
		}
		iteration = step = 0;
	}

	/**
	 * Rdb.
	 */
	void rdb() {
		int	i,k;

		for (i=0; i<n; i++)
			v[i].delta_plus = v[i].delta_minus = -1;
		for (i=0; i<m; i++)
			e[i].delta_plus = e[i].delta_minus = -1;
		for (i=0; i<m; i++) {
			k = e[i].rndd_plus;
			if (v[k].delta_plus == -1)
				v[k].delta_plus = i;
			else {
				k = v[k].delta_plus;
				while(e[k].delta_plus >= 0)
					k = e[k].delta_plus;
				e[k].delta_plus = i;
			}
			k = e[i].rndd_minus;
			if (v[k].delta_minus == -1)
				v[k].delta_minus = i;
			else {
				k = v[k].delta_minus;
				while(e[k].delta_minus >= 0)
					k = e[k].delta_minus;
				e[k].delta_minus = i;
			}
		}
	}

	/**
	 * Append_pre_s.
	 *
	 * @param i the i
	 */
	void append_pre_s(int i) {
		v[i].succ = v[i].pred = -1;
		if (pre_s_first<0)
			pre_s_first = i;
		else
			v[pre_s_last].succ = i;
		v[i].pred = pre_s_last;
		pre_s_last = i;
	}

	/**
	 * Remove_pre_s.
	 *
	 * @param i the i
	 */
	void remove_pre_s(int i) {
		int	succ = v[i].succ;
		int	pred = v[i].pred;

		if (succ>=0)
			v[succ].pred = pred;
		else
			pre_s_last = pred;
		if (pred>=0)
			v[pred].succ = succ;
		else
			pre_s_first = succ;
	}

	/**
	 * Step1.
	 */
	void step1() {		/* initialize */
		u = snode;
		for (int i=0; i<n; i++) {
			v[i].succ = v[i].pred = -2;
			v[i].prev = v[i].dist = -1;
		}
		v[u].succ = -3;
		v[u].dist = 0;
		pre_s_first = pre_s_last = -1;
	}

	/**
	 * Step2.
	 */
	void step2() {		/* replace labels */
		int i,j;

		j = v[u].delta_plus;
		while (j>=0) {
			i = e[j].rndd_minus;
			if ((v[i].succ>=-2)&&((v[i].dist<0)||
				(v[i].dist>v[u].dist+e[j].len))) {
				if (v[i].dist<0)
					append_pre_s(i);
				v[i].dist = v[u].dist + e[j].len;
				v[i].prev = u;	  /* label */
			}
			j = e[j].delta_plus;
		}
		if (!isdigraph) {
		j = v[u].delta_minus;
		while (j>=0) {
			i = e[j].rndd_plus;
			if ((v[i].succ>=-2)&&((v[i].dist<0)||
				(v[i].dist>v[u].dist+e[j].len))) {
				if (v[i].dist<0)
					append_pre_s(i);
				v[i].dist = v[u].dist + e[j].len;
				v[i].prev = u;	  /* label */
			}
			j = e[j].delta_minus;
		}
		}
		v[u].succ = -4;
	}

	/**
	 * Step3.
	 */
	void step3() {		/* find the shortest node in Sbar */
		int i,rho;

		rho = -1;
		for (i = pre_s_first; i>=0; i = v[i].succ) {
			if ((rho < 0)||(rho>v[i].dist)) {
				rho = v[i].dist;
				u = i;
			}
		}
		remove_pre_s(u);
		v[u].succ = -3;
                if (v[u].id == p2) {
                  //remonter le chemin en creant le vecteur de sortie
                  int k=0;
                  while (true) {
                    findGoal = true;
                    chemin.insertElementAt(new ItineraryState(v[u].id, v[u].prev), 0);
                    if (v[u].id == p1)
                      break;
                    if (v[u].prev >= 0)
                      u = v[u].prev;
                    else {
                      chemin.clear();
                      break;
                    }
                    k++;
                    if (k > n) {
                      chemin.clear();
                      break;
                    }
                  }
                }
	}

	/**
	 * Step4.
	 */
	void step4() {
		v[u].succ = -4;
	}

	/**
	 * Weight.
	 *
	 * @param n the n
	 * @param x the x
	 * @param y the y
	 * @return the double
	 */
	double weight(Node n, double x, double y) {
		double	w,z,xx,yy;

		w = 0;
		for (int j = n.delta_plus; j>=0; j=e[j].delta_plus) {
			xx = (double)(v[e[j].rndd_minus].x - n.x);
			yy = (double)(v[e[j].rndd_minus].y - n.y);
			z = (x*xx+y*yy)/Math.sqrt((x*x+y*y)*(xx*xx+yy*yy))+1.0;
			w += z*z*z*z;
		}
		for (int j = n.delta_minus; j>=0; j=e[j].delta_minus) {
			xx = (double)(v[e[j].rndd_plus].x - n.x);
			yy = (double)(v[e[j].rndd_plus].y - n.y);
			z = (x*xx+y*yy)/Math.sqrt((x*x+y*y)*(xx*xx+yy*yy))+1.0;
			w += z*z*z*z;
		}
		return w;
	}

	/**
	 * Init_sub.
	 */
	void init_sub() {
		int x[] = {1, 0, -1, 1, 0, -1};
		int y[] = {1, 1, 1, -1, -1, -1};
		int 	i,j,k;
		double	w,z;

		for (i=0; i<n; i++) {
			k=0;
			w=weight(v[i],(double)x[0],(double)y[0]);
			for (j=1; j<6; j++) {
				z=weight(v[i],(double)x[j],(double)y[j]);
				if (z<w) {
					w = z;
					k = j;
				}
			}
			v[i].dx = x[k];
			v[i].dy = y[k];
		}
	}

    /**
     * Initialize
     *
     * @param res the road network
     * @param z the zoom
     */
    public void init(RoadNetwork res, float z) {
      reseau = res;
      zoom = z;
      n = reseau.getNombrePoints();
      m = reseau.getNombreConnexions();
      v = new Node[n];
      e = new Edge[m];
      input_graph(reseau);
      findGoal = false;
      chemin.clear();
      iteration = step = 0;
      snode = 0;
      rdb();
      init_sub();
    }

	/**
	 * Solve.
	 *
	 * @param ps the ps
	 * @param pe the pe
	 * @return the itinerary
	 */
	public Vector<ItineraryState> solve(int ps, int pe) {
          iteration = step = 0;
          p1 = ps;
          p2 = pe;
          findGoal = false;
          chemin.clear();
          if (p1 == p2) {
            chemin.insertElementAt(new ItineraryState(p1, p2), 0);
            return chemin;
          }
          snode = findNode(p1);
          enode = findNode(p2);
          rdb();
          init_sub();
          Exec();
          return chemin;
	}

	/**
	 * Xy.
	 *
	 * @param a the a
	 * @param b the b
	 * @param w the w
	 * @param h the h
	 * @return the int[]
	 */
	int [] xy(int a, int b, int w, int h) {
		int	x[] = new int[2];

		if (Math.abs(w*b)>=Math.abs(h*a)) {
			x[0] = ((b>=0)?1:-1)*a*h/b/2;
			x[1] = ((b>=0)?1:-1)*h/2;
		} else {
			x[0] = ((a>=0)?1:-1)*w/2;
			x[1] = ((a>=0)?1:-1)*b*w/a/2;
		}
		return x;
	}

	/**
	 * Exec.
	 */
	public void Exec() {
          while (iteration<n) {
            if (iteration == 0) {
              step1();
              iteration++;
              step = 2;
            }
            else {
              if (step == 2) {
                step2();
                step = 3;
              }
              else {
                step3();
                iteration++;
                step = 2;
              }
            }
            if (findGoal)
                break;
          }
          step4();
	}
}

