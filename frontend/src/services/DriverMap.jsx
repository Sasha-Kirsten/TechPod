import React, {useState, useEffect} from 'react';
import {GoogleMap, Marker, useLoadScript} from '@react-google-maps/api';

const mapContainerStyle = {
  width: '100%',
  height: '400px',
};

const center = {
  lat: 37.7749, // Default latitude (San Francisco)
  lng: -122.4194, // Default longitude (San Francisco)
};

const DriverMap = ({ deliveryPoints, driverLocation }) => {
  const { isLoaded, loadError } = useLoadScript({
    googleMapsApiKey: 'AIzaSyDbYrojZMUXUxhr0G3W-tgWSxYJqatlsso', // Replace with your Google Maps API key
  });
  if (loadError) return <div>Error loading maps</div>;
  if (!isLoaded) return <div>Loading Maps</div>;

  return (
    <GoogleMap
      mapContainerStyle={mapContainerStyle}
      zoom={12}
      center={driverLocation[0] || center}
    >
      {deliveryPoints.map((point, index) => (
        point.lat && point.lng && (
        <Marker key={`delivery-${index}`} position={{ lat: point.lat, lng: point.lng }}
        label="📍"
        />
        )
      ))}
      {driverLocation.map((driver, index) => (
        driver.lat && driver.lng && (
        <Marker
          key={`driver-${index}`}
          position={{ lat: driver.lat, lng: driver.lng }}
          label="🚚"
        />
        )
      ))}
    </GoogleMap>
  );
};

export default DriverMap;