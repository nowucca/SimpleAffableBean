# SimpleAffableBean
A rewrite of the Affable Bean EJB web app, moving away from EJB and using a simpler technology stack.

This software is an adaptation from the Affable Bean project, released under the 2-clause Berkeley licence by Sun and then Oracle.
Please see the LICENCE file for compliance and licensing concerns.

The starting point for this project is the complete Affable beans project at: https://netbeans.org/projects/samples/downloads/download/Samples/JavaEE/ecommerce/AffableBean_complete.zip

## Problems Being Addressed

Hardly anyone I know uses the EJB stack as it was meant to be used, with session and entity beans, data sources, services and the rest of it.  I've found that some of the best technology stacks avoid too much abstraction, and are straightforward to use, and lead to code that is straightforward to reason about.  I would classify a full-blown EJB container as getting low-marks in those areas.

## Solutions Being Proposed

The goal of this rewrite is to enable the use of a pure servlet container (such as Tomcat or Jetty) to run the AffableBean project.
To achieve this, we will be writing our own DataSource wrapper, writing a separate business logic layer (services and daos) and using a combination of servlets and JSPs for the front layer (including the ViewModel pattern of modelling the view at the servlet layer with bean classes). 

As progress is made, I'll be tweaking the implementation here and perhaps strategies as I learn limitations along the way.
